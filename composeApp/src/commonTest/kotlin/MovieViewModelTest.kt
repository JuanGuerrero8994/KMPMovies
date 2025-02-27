import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import io.ktor.client.HttpClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.data.remote.TMDBApi
import org.devjg.kmpmovies.data.repository.MovieRepositoryImpl
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.repository.MovieRepository
import org.devjg.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import org.devjg.kmpmovies.ui.screen.home.MovieViewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.DefaultAsserter.assertEquals
import kotlin.test.assertTrue
import kotlin.test.expect
import kotlin.test.fail
import kotlin.time.Duration.Companion.seconds


@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest : KoinTest {
    private val viewModel: MovieViewModel by inject()


    private val testModule = module {
        // Cliente HTTP real
        single { HttpClient() }

        // API real
        single { TMDBApi() }

        // Repositorio real
        single<MovieRepository> { MovieRepositoryImpl(get()) }

        // Caso de uso
        factory { GetPopularMoviesUseCase(get()) }

        // ViewModel
        single { MovieViewModel(get()) }
    }

    @BeforeTest
    fun setup() {
        stopKoin()  // Asegura que no haya conflictos con otras instancias de Koin
        startKoin { modules(testModule) }
    }

    @Test
    fun `fetchPopularMovies deberia obtener peliculas exitosamente`() =
        runTest(timeout = 10.seconds) {

            viewModel.fetchPopularMovies()

            viewModel.moviesState.test {

                val firstState = awaitItem()
                assertTrue(firstState is Resource.Loading, "Debería iniciar en Loading")

                // 2️⃣ Avanzamos la ejecución de coroutines
                advanceUntilIdle()


                // 3️⃣ Esperamos el siguiente estado
                val result = awaitItem()

                when (result) {
                    is Resource.Success -> {
                        assertTrue(
                            result.data.isNotEmpty(),
                            "La lista de películas no debería estar vacía"
                        )
                    }

                    is Resource.Error -> {
                        fail("No debería haber un error, se esperaba una lista de películas")
                    }

                    else -> fail("Estado inesperado: $result")
                }

                // 3️⃣ No debe haber más emisiones
                expectNoEvents()
            }


        }

    @Test
    fun `fetchPopularMovies debería manejar una excepción correctamente`() = runTest {
        // Crear un mock para el repositorio
        val movieRepository = mock<MovieRepository>()

        // Mockear el método suspendido del repositorio
        everySuspend { movieRepository.getPopularMovies() } returns flow {
            emit(Resource.Error(Exception("Error Forzado")))
        }

        // Llamar al método en el ViewModel
        viewModel.fetchPopularMovies()

        // Verificar que se haya llamado a getPopularMovies
        verifySuspend { movieRepository.getPopularMovies() }

        // Probar el estado de las películas
        viewModel.moviesState.test {
            // Esperamos que el primer estado sea de carga
            val firstState = awaitItem()
            assertTrue(firstState is Resource.Loading, "Debería iniciar en Loading")

            // Avanzamos las corrutinas
            advanceUntilIdle()

            // Esperamos que el siguiente estado sea un error
            val result = awaitItem()
            when (result) {
                is Resource.Error -> {
                    assertTrue(result.exception.message!!.contains("Error Forzado"), "Debería haber un error con el mensaje correcto")
                }
                else -> fail("Se esperaba un error pero obtuvimos: $result")
            }

            // No debe haber más emisiones
            expectNoEvents()
        }
    }

}





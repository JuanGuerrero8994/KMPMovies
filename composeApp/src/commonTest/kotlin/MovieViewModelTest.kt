import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import io.ktor.client.HttpClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.data.remote.TMDBApi
import org.devjg.kmpmovies.data.repository.MovieRepositoryImpl
import org.devjg.kmpmovies.domain.repository.MovieRepository
import org.devjg.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import org.devjg.kmpmovies.ui.screen.home.MovieViewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue
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
        runTest(timeout = 20.seconds) {

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
                        assertTrue(result.data.isNotEmpty(), "La lista de películas no debería estar vacía")
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
    fun `fetchPopularMovies debería manejar una excepción correctamente`() = runTest(timeout = 20.seconds) {
        // Crear un mock para el repositorio
        val movieRepository = mock<MovieRepository>()

        // Mockear el método suspendido del repositorio para devolver un flujo con un error
        everySuspend { movieRepository.getPopularMovies() } returns flow {
            emit(Resource.Error(Exception("Error")))
        }

        // Reiniciar Koin con el mock en lugar del repositorio real
        stopKoin()
        startKoin {
            modules(
                module {
                    single { movieRepository } // Usar el mock en Koin
                    factory { GetPopularMoviesUseCase(get()) }
                    single { MovieViewModel(get()) }
                }
            )
        }

        // Obtener la instancia de ViewModel después de reiniciar Koin
        val viewModel: MovieViewModel by inject()

        // Llamar al método en el ViewModel
        viewModel.fetchPopularMovies()

        // Probar el estado de las películas
        viewModel.moviesState.test {
            // 1️⃣ Esperamos que el primer estado sea de carga
            val firstState = awaitItem()
            assertTrue(firstState is Resource.Loading, "Debería iniciar en Loading")

            // 2️⃣ Avanzamos las corrutinas
            advanceUntilIdle()

            // 3️⃣ Esperamos que el siguiente estado sea un error
            when (val result = awaitItem()) {
                is Resource.Error -> {
                    assertTrue(result.exception.message!!.contains("Error"), "Debería haber un error con el mensaje correcto")
                }
                else -> fail("Se esperaba un error pero obtuvimos: $result")
            }

            // 4️⃣ No debe haber más emisiones
            expectNoEvents()
        }
    }


}





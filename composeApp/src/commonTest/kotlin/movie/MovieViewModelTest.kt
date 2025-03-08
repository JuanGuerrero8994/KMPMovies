package movie

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import io.ktor.client.HttpClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.data.remote.TMDBApi
import org.devjg.kmpmovies.data.repository.MovieRepositoryImpl
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.repository.MovieRepository
import org.devjg.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import org.devjg.kmpmovies.ui.screen.movie.MovieViewModel
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
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
        stopKoin()  // Detener cualquier instancia de Koin previamente iniciada
        startKoin { modules(testModule) }  // Cargar el módulo para este test
    }

    @AfterTest
    fun tearDown() {
        stopKoin()  // Detener Koin después de cada test
    }



    @Test
    fun `fetchPopularMovies debería obtener películas exitosamente con datos específicos`() = runTest(timeout = 30.seconds) {
        // Crear un mock para el repositorio
        val movieRepository = mock<MovieRepository>()

        // Mockear el método suspendido del repositorio para devolver un flujo con una lista de películas específica
        val mockMoviesList = List(20) { index ->
            Movie(
                id = index + 1,
                title = "Movie ${index + 1}",
            )
        }

        // Usar flowOf para evitar problemas de ejecución en coroutines
        everySuspend { movieRepository.getPopularMovies() } returns flowOf(Resource.Success(mockMoviesList))

        // Crear el ViewModel manualmente en lugar de usar Koin para asegurarnos de que usa el mock
        val viewModel = MovieViewModel(GetPopularMoviesUseCase(movieRepository))

        // Llamar al método en el ViewModel
        viewModel.fetchPopularMovies()

        // Probar el estado de las películas
        viewModel.moviesState.test {

            // 3️⃣ Esperamos el siguiente estado
            val result = awaitItem()
            advanceUntilIdle()

            // 3️⃣ Esperamos que el siguiente estado sea un éxito con los datos correctos
            when (result) {
                is Resource.Success -> {
                    assertTrue(
                        result.data.isNotEmpty(),
                        "La lista de películas no debería estar vacía"
                    )
                    assertEquals(
                        mockMoviesList.size,
                        result.data.size,
                        "La lista de películas debería tener el mismo tamaño que la lista mockeada"
                    )
                    assertTrue(
                        result.data.containsAll(mockMoviesList),
                        "La lista de películas debería contener todas las películas esperadas"
                    )
                }

                else -> fail("Se esperaba un éxito, pero obtuvimos: $result")
            }

            // Verificar que el método fue llamado exactamente una vez
            verifySuspend { movieRepository.getPopularMovies() }

            // 4️⃣ No debe haber más emisiones
            expectNoEvents()
        }
    }


    @Test
    fun `fetchPopularMovies debería obtener peliculas exitosamente`() =
        runTest(timeout = 30.seconds) {

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
    fun `fetchPopularMovies debería manejar una excepción correctamente`() =
        runTest(timeout = 30.seconds) {
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
                        assertTrue(
                            result.exception.message!!.contains("Error"),
                            "Debería haber un error con el mensaje correcto"
                        )
                    }

                    else -> fail("Se esperaba un error pero obtuvimos: $result")
                }

                // 4️⃣ No debe haber más emisiones
                expectNoEvents()
            }
        }

}

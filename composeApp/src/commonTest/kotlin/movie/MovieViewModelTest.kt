package movie

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.repository.MovieRepository
import org.devjg.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import org.devjg.kmpmovies.ui.screen.movie.MovieViewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.koin.test.get
import kotlin.jvm.JvmStatic
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest : KoinTest {

    private val movieRepository: MovieRepository = mock()  // Mock de MovieRepository
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository)
    private lateinit var viewModel: MovieViewModel

    // Inicializar Koin antes de cada prueba
    @BeforeTest
    fun setupKoin() {
        stopKoin()  // Detener cualquier Koin previamente inicializado

        // Definir el módulo de Koin para las pruebas
        val testModule: Module = module {
            single { movieRepository }  // Usar el mock
            factory { getPopularMoviesUseCase }
            factory { MovieViewModel(getPopularMoviesUseCase) }  // ViewModel con las dependencias
        }

        // Inicializar Koin con el módulo de prueba
        startKoin {
            modules(testModule)
        }

        // Inicializar el ViewModel
        viewModel = get()
    }

    // Detener Koin después de cada prueba
    @AfterTest
    fun tearDown() {
        stopKoin()  // Limpiar Koin después de cada prueba
    }

    @Test
    fun `fetchPopularMovies should handle success with mock data`() = runTest {
        val mockMoviesList = List(10) { index -> Movie(id = index + 1, title = "Movie ${index + 1}") }

        // Simular la respuesta del repository
        everySuspend { movieRepository.getPopularMovies() } returns flowOf(Resource.Success(mockMoviesList))

        // Llamar a la función para obtener las películas
        viewModel.fetchPopularMovies()

        // Verificar el estado de las películas
        viewModel.moviesState.test {
            when (val result = awaitItem()) {
                is Resource.Loading -> {
                    advanceUntilIdle()
                    awaitItem()  // Asegurarse de que la carga se complete
                }
                is Resource.Success -> {
                    // Verificar que la lista de películas sea correcta
                    assertEquals(mockMoviesList.size, result.data.size)
                    assertTrue(result.data.containsAll(mockMoviesList))
                }
                else -> fail("Se esperaba éxito, pero obtuvo: $result")
            }
            expectNoEvents()
        }
    }

    @Test
    fun `fetchPopularMovies should handle an exception correctly`() = runTest {
        everySuspend { movieRepository.getPopularMovies() } returns flowOf(Resource.Error(Exception("Error en la solicitud")))

        viewModel.fetchPopularMovies()

        viewModel.moviesState.test {
            when (val result = awaitItem()) {
                is Resource.Loading -> {
                    advanceUntilIdle()
                    awaitItem()
                }
                is Resource.Error -> {
                    assertTrue(result.exception.message!!.contains("Error en la solicitud"))
                }
                else -> fail("Se esperaba un error, pero obtuvo: $result")
            }
            expectNoEvents()
        }
    }
}

package movie

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.flow.flow
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
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
        everySuspend { movieRepository.getPopularMovies() } returns flow {
            emit(Resource.Loading)
            emit(Resource.Success(mockMoviesList))
        }

        // Llamar a la función para obtener las películas
        viewModel.fetchPopularMovies()

        // Verificar el estado de las películas
        viewModel.moviesState.test {
            // Verificar que se emita un estado de Loading primero
            val loadingState = awaitItem()
            assertTrue(loadingState is Resource.Loading)

            // Luego verificar que se emita un estado de Success
            val result = awaitItem()
            assertTrue(result is Resource.Success)
            assertEquals(mockMoviesList.size, (result as Resource.Success).data.size)
            assertTrue(result.data.containsAll(mockMoviesList))

            // Asegurarse de que no haya eventos pendientes
            expectNoEvents()
        }
    }

    @Test
    fun `fetchPopularMovies should handle an exception correctly`() = runTest {
        // Simular la respuesta del repository con un error
        everySuspend { movieRepository.getPopularMovies() } returns flow {
            emit(Resource.Loading)
            emit(Resource.Error(Exception("Error en la solicitud")))
        }

        viewModel.fetchPopularMovies()

        viewModel.moviesState.test {
            // Verificar que se emita un estado de Loading primero
            val loadingState = awaitItem()
            assertTrue(loadingState is Resource.Loading)

            // Luego verificar que se emita un estado de Error
            val result = awaitItem()
            assertTrue(result is Resource.Error)
            assertTrue((result as Resource.Error).exception.message!!.contains("Error en la solicitud"))

            // Asegurarse de que no haya eventos pendientes
            expectNoEvents()
        }
    }
}

package movie

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.repository.MovieRepository
import org.devjg.kmpmovies.domain.usecases.GetDetailMovieUseCase
import org.devjg.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import org.devjg.kmpmovies.domain.usecases.GetTopRatedMoviesUseCase
import org.devjg.kmpmovies.ui.screen.movie.MovieViewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetTopRatedMoviesTest : KoinTest {

    private val testDispatcher = StandardTestDispatcher()
    private val movieRepository: MovieRepository = mock()
    private val getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository)
    private val getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(movieRepository)
    private val getDetailMovieUseCase = GetDetailMovieUseCase(movieRepository)
    private lateinit var viewModel: MovieViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Cambiar el Dispatcher principal a testDispatcher

        val testModule = module {
            single { movieRepository }
            factory { getPopularMoviesUseCase }
            factory { getTopRatedMoviesUseCase }
            factory { MovieViewModel(getPopularMoviesUseCase, getTopRatedMoviesUseCase,getDetailMovieUseCase) }
        }

        startKoin { modules(testModule) }

        viewModel = get()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `fetchTopRatedMovies should handle success with mock data`() = runTest {
        val mockMoviesList =
            List(10) { index -> Movie(id = index + 1, title = "Movie ${index + 1}") }

        everySuspend { movieRepository.getTopRatedMovies() } returns flow {
            emit(Resource.Success(mockMoviesList))
        }

        viewModel.fetchTopRatedMovies()

        viewModel.topRatedMoviesState.test {
            assertTrue(awaitItem() is Resource.Loading)
            val result = awaitItem()
            assertTrue(result is Resource.Success)
            assertEquals(mockMoviesList, (result).data)
            expectNoEvents()
        }
    }

    @Test
    fun `fetchTopRatedMovies should handle an exception correctly`() = runTest {
        everySuspend { movieRepository.getTopRatedMovies() } returns flow {
            emit(Resource.Loading)
            emit(Resource.Error(Exception("Error en la solicitud")))
        }

        viewModel.fetchTopRatedMovies()

        viewModel.topRatedMoviesState.test {
            assertTrue(awaitItem() is Resource.Loading)
            val result = awaitItem()
            assertTrue(result is Resource.Error)
            assertTrue(result.exception.message!!.contains("Error en la solicitud"))
            expectNoEvents()
        }
    }
}


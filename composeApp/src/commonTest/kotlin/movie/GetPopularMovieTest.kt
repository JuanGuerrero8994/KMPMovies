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
import org.devjg.kmpmovies.domain.usecases.GetDetailPersonUseCase
import org.devjg.kmpmovies.domain.usecases.GetMovieCastUseCase
import org.devjg.kmpmovies.domain.usecases.GetMovieSimilarUseCase
import org.devjg.kmpmovies.domain.usecases.GetMoviesForActorUseCase
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
class GetPopularMovieTest : KoinTest {

    private val testDispatcher = StandardTestDispatcher()
    private val movieRepository: MovieRepository = mock()
    private val getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository)
    private val getTopRatedMoviesUseCase = GetTopRatedMoviesUseCase(movieRepository)
    private val getDetailMovieUseCase = GetDetailMovieUseCase(movieRepository)
    private val getMovieCastUseCase = GetMovieCastUseCase(movieRepository)
    private val getMovieSimilarUseCase = GetMovieSimilarUseCase(movieRepository)
    private val getDetailPersonUseCase = GetDetailPersonUseCase(movieRepository)
    private val getMoviesForActorUseCase = GetMoviesForActorUseCase(movieRepository)


    private lateinit var viewModel: MovieViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher) 

        val testModule = module {
            single { movieRepository }
            factory { getPopularMoviesUseCase }
            factory { getTopRatedMoviesUseCase }
            factory { getDetailMovieUseCase }
            factory { getMovieCastUseCase }
            factory { getMovieSimilarUseCase }
            factory { MovieViewModel(getPopularMoviesUseCase, getTopRatedMoviesUseCase,getDetailMovieUseCase,getMovieCastUseCase,getMovieSimilarUseCase,getDetailPersonUseCase,getMoviesForActorUseCase) }
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
    fun `fetchPopularMovies should handle success with mock data`() = runTest {
        val mockMoviesList =
            List(10) { index -> Movie(id = index + 1, title = "Movie ${index + 1}") }

        everySuspend { movieRepository.getPopularMovies() } returns flow {
            emit(Resource.Success(mockMoviesList))
        }

        viewModel.fetchPopularMovies()

        viewModel.moviesState.test {
            assertTrue(awaitItem() is Resource.Loading)
            val result = awaitItem()
            assertTrue(result is Resource.Success)
            assertEquals(mockMoviesList, (result).data)
            expectNoEvents()
        }
    }

    @Test
    fun `fetchPopularMovies should handle an exception correctly`() = runTest {
        everySuspend { movieRepository.getPopularMovies() } returns flow {
            emit(Resource.Loading)
            emit(Resource.Error(Exception("Error en la solicitud")))
        }

        viewModel.fetchPopularMovies()

        viewModel.moviesState.test {
            assertTrue(awaitItem() is Resource.Loading)
            val result = awaitItem()
            assertTrue(result is Resource.Error)
            assertTrue(result.exception.message!!.contains("Error en la solicitud"))
            expectNoEvents()
        }
    }
}


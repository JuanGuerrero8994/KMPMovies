package movie

import org.devjg.kmpmovies.domain.model.Person


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
import org.devjg.kmpmovies.domain.model.MovieDetail
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
class GetPersonDetailTest : KoinTest {

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
    fun `fetchPersonDetail should handle success with mock data`() = runTest {
        val mockMovie = MovieDetail(id = 1,"Mission Imposible")

        everySuspend { movieRepository.getDetailMovie(1) } returns flow {
            emit(Resource.Success(mockMovie))
        }

        val mockPerson = Person(id = 1,"Tom Cruise")

        everySuspend { movieRepository.getPersonDetails(1) } returns flow {
            emit(Resource.Success(mockPerson))
        }

        viewModel.fetchDetailPerson(1)

        viewModel.personState.test {
            assertTrue(awaitItem() is Resource.Loading)
            val result = awaitItem()
            assertTrue(result is Resource.Success)
            assertEquals(mockPerson, result.data)
            expectNoEvents()
        }
    }

    @Test
    fun `fetchPersonDetail should handle an exception correctly`() = runTest {
        everySuspend { movieRepository.getPersonDetails(1) } returns flow {
            emit(Resource.Loading)
            emit(Resource.Error(Exception("Person not found")))
        }

        viewModel.fetchDetailPerson(1)

        viewModel.personState.test {
            assertTrue(awaitItem() is Resource.Loading)
            val result = awaitItem()
            assertTrue(result is Resource.Error)
            assertTrue(result.exception.message!!.contains("Person not found"))
            expectNoEvents()
        }
    }
}


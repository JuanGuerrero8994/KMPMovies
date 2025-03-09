package movie

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import io.ktor.client.HttpClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest : KoinTest {

    private val viewModel: MovieViewModel by inject()

    private val testModule = module {
        single { HttpClient() }
        single { TMDBApi() }
        single<MovieRepository> { MovieRepositoryImpl(get()) }
        factory { GetPopularMoviesUseCase(get()) }
        single { MovieViewModel(get()) }
    }

    @BeforeTest
    fun setup() {
        stopKoin()
        startKoin { modules(testModule) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `fetchPopularMovies should fetch real data successfully`() = runTest {
        viewModel.fetchPopularMovies()

        viewModel.moviesState.test {
            when (val result = awaitItem()) {
                is Resource.Loading -> {
                    advanceUntilIdle()
                    awaitItem()
                }
                is Resource.Success -> {
                    assertTrue(result.data.isNotEmpty(), "Movie list should not be empty")
                }
                is Resource.Error -> {
                    fail("Expected a successful movie list, but got an error")
                }
                else -> fail("Unexpected state: $result")
            }
            expectNoEvents()
        }
    }

    @Test
    fun `fetchPopularMovies should handle success with mock data`() = runTest {
        val movieRepository = mock<MovieRepository>()
        val mockMoviesList = List(20) { index -> Movie(id = index + 1, title = "Movie ${index + 1}") }

        everySuspend { movieRepository.getPopularMovies() } returns flowOf(Resource.Success(mockMoviesList))

        stopKoin()
        startKoin {
            modules(
                module {
                    single { movieRepository }
                    factory { GetPopularMoviesUseCase(get()) }
                    single { MovieViewModel(get()) }
                }
            )
        }
        val viewModel = MovieViewModel(GetPopularMoviesUseCase(movieRepository))

        viewModel.fetchPopularMovies()

        viewModel.moviesState.test {
            when (val result = awaitItem()) {
                is Resource.Loading -> {
                    advanceUntilIdle()
                    awaitItem()
                }
                is Resource.Success -> {
                    assertTrue(result.data.isNotEmpty(), "Movie list should not be empty")
                    assertEquals(mockMoviesList.size, result.data.size, "Movie list size should match the mock list")
                    assertTrue(result.data.containsAll(mockMoviesList), "Movie list should contain all expected movies")
                }
                else -> fail("Expected success, but got: $result")
            }
            verifySuspend { movieRepository.getPopularMovies() }
            expectNoEvents()
        }
    }

    @Test
    fun `fetchPopularMovies should handle an exception correctly`() = runTest {
        val movieRepository = mock<MovieRepository>()

        everySuspend { movieRepository.getPopularMovies() } returns flow {
            emit(Resource.Error(Exception("Error")))
        }

        stopKoin()
        startKoin {
            modules(
                module {
                    single { movieRepository }
                    factory { GetPopularMoviesUseCase(get()) }
                    single { MovieViewModel(get()) }
                }
            )
        }
        val viewModel = MovieViewModel(GetPopularMoviesUseCase(movieRepository))

        viewModel.fetchPopularMovies()

        viewModel.moviesState.test {
            when (val result = awaitItem()) {
                is Resource.Loading -> {
                    advanceUntilIdle()
                    awaitItem()
                }
                is Resource.Error -> {
                    assertTrue(result.exception.message!!.contains("Error"), "Error message should be correct")
                }
                is Resource.Success -> {
                    advanceUntilIdle()
                }
                else -> fail("Expected an error but got: $result")
            }
            expectNoEvents()
        }
    }
}

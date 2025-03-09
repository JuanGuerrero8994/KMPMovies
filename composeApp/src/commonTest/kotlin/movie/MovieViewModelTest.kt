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
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.stopKoin
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class MovieViewModelTest : KoinTest {

    private val movieRepository = mock<MovieRepository>()
    private val getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository)
    private lateinit var viewModel: MovieViewModel

    // Setup Koin only once before all tests
    @BeforeTest
    fun setupKoin() {
        startKoin {
            modules(
                module {
                    single { movieRepository }
                    factory { getPopularMoviesUseCase }
                    factory { MovieViewModel(getPopularMoviesUseCase) }
                }
            )
        }
    }

    // Stop Koin after each test
    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `fetchPopularMovies should handle success with mock data`() = runTest {
        val mockMoviesList = List(10) { index -> Movie(id = index + 1, title = "Movie ${index + 1}") }
        everySuspend { movieRepository.getPopularMovies() } returns flowOf(Resource.Success(mockMoviesList))

        // Lazy initialize viewModel after Koin setup
        viewModel = get()

        viewModel.fetchPopularMovies()

        viewModel.moviesState.test {
            when (val result = awaitItem()) {
                is Resource.Loading -> {
                    advanceUntilIdle()
                    awaitItem()
                }
                is Resource.Success -> {
                    assertEquals(mockMoviesList.size, result.data.size, "Movie list size should match")
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
        everySuspend { movieRepository.getPopularMovies() } returns flow { emit(Resource.Error(Exception("Error"))) }

        // Lazy initialize viewModel after Koin setup
        viewModel = get()

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
                else -> fail("Expected an error but got: $result")
            }
            expectNoEvents()
        }
    }
}

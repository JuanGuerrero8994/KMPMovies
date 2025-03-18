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
import org.devjg.kmpmovies.domain.model.TVShow
import org.devjg.kmpmovies.domain.repository.TVShowRepository
import org.devjg.kmpmovies.domain.usecases.GetDetailPersonUseCase
import org.devjg.kmpmovies.domain.usecases.GetMoviesForActorUseCase
import org.devjg.kmpmovies.domain.usecases.GetTVShowTopRatedUseCase
import org.devjg.kmpmovies.ui.screen.tvShowTopRated.TVShowViewModel
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
class GetTvShowTopRatedTest : KoinTest {

    private val testDispatcher = StandardTestDispatcher()
    private val tvShowRepository: TVShowRepository = mock()
    private val getTvShowUseCase = GetTVShowTopRatedUseCase(tvShowRepository)

    private lateinit var viewModel: TVShowViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        val testModule = module {
            single { tvShowRepository }
            factory { getTvShowUseCase }
            factory { TVShowViewModel(getTvShowUseCase) }
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
    fun `fetchTVShowTopRated should handle success with mock data`() = runTest {
        val mockTVShowsList =
            List(10) { index -> TVShow(id = index + 1, title = "TV Show ${index + 1}") }

        everySuspend { tvShowRepository.getTVShowTopRated() } returns flow {
            emit(Resource.Success(mockTVShowsList))
        }

        viewModel.fetchTVShowTopRated()

        viewModel.tvShowsState.test {
            assertTrue(awaitItem() is Resource.Loading)
            val result = awaitItem()
            assertTrue(result is Resource.Success)
            assertEquals(mockTVShowsList, (result).data)
            expectNoEvents()
        }
    }

    @Test
    fun `fetchTopRatedMovies should handle an exception correctly`() = runTest {
        everySuspend { tvShowRepository.getTVShowTopRated() } returns flow {
            emit(Resource.Loading)
            emit(Resource.Error(Exception("Error en la solicitud")))
        }

        viewModel.fetchTVShowTopRated()

        viewModel.tvShowsState.test {
            assertTrue(awaitItem() is Resource.Loading)
            val result = awaitItem()
            assertTrue(result is Resource.Error)
            assertTrue(result.exception.message!!.contains("Error en la solicitud"))
            expectNoEvents()
        }
    }
}


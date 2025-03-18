package org.devjg.kmpmovies.ui.screen.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Cast
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.model.MovieDetail
import org.devjg.kmpmovies.domain.model.Person
import org.devjg.kmpmovies.domain.usecases.GetDetailMovieUseCase
import org.devjg.kmpmovies.domain.usecases.GetDetailPersonUseCase
import org.devjg.kmpmovies.domain.usecases.GetMovieCastUseCase
import org.devjg.kmpmovies.domain.usecases.GetMovieSimilarUseCase
import org.devjg.kmpmovies.domain.usecases.GetMoviesForActorUseCase
import org.devjg.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import org.devjg.kmpmovies.domain.usecases.GetTopRatedMoviesUseCase

class MovieViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getDetailMovieUseCase: GetDetailMovieUseCase,
    private val getMovieCastUseCase: GetMovieCastUseCase,
    private val getMovieSimilarUseCase: GetMovieSimilarUseCase,
    private val getDetailPerson: GetDetailPersonUseCase,
    private val getMoviesForActorUseCase: GetMoviesForActorUseCase
    ) : ViewModel() {

    private val _moviesState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val moviesState: StateFlow<Resource<List<Movie>>> get() = _moviesState

    private val _topRatedMoviesState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val topRatedMoviesState: StateFlow<Resource<List<Movie>>> get() = _topRatedMoviesState

    private val _movieDetailState = MutableStateFlow<Resource<MovieDetail>>(Resource.Loading)
    val movieDetailState: StateFlow<Resource<MovieDetail>> get() = _movieDetailState

    private val _movieCastState = MutableStateFlow<Resource<List<Cast>>>(Resource.Loading)
    val movieCastState: StateFlow<Resource<List<Cast>>> get() = _movieCastState

    private val _moviesSimilarState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val moviesSimilarState: StateFlow<Resource<List<Movie>>> get() = _moviesSimilarState


    private val _personState = MutableStateFlow<Resource<Person>>(Resource.Loading)
    val personState: StateFlow<Resource<Person>> get() = _personState

    private val _moviesForActorState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val moviesForActorState : StateFlow<Resource<List<Movie>>> = _moviesForActorState

    /**
     * Función genérica para evitar repetición en los fetch
     */
    private fun <T> fetchData(stateFlow: MutableStateFlow<Resource<T>>, useCase: suspend () -> kotlinx.coroutines.flow.Flow<Resource<T>>) {
        viewModelScope.launch {
            useCase().collectLatest { resource ->
                stateFlow.value = resource
            }
        }
    }

    fun fetchPopularMovies() = fetchData(_moviesState) { getPopularMoviesUseCase.invoke() }
    fun fetchTopRatedMovies() = fetchData(_topRatedMoviesState) { getTopRatedMoviesUseCase.invoke() }
    fun fetchMovieDetail(movieId: Int) = fetchData(_movieDetailState) { getDetailMovieUseCase.invoke(movieId) }
    fun fetchMovieCast(movieId: Int) = fetchData(_movieCastState) { getMovieCastUseCase.invoke(movieId) }
    fun fetchMovieSimilar(movieId: Int) = fetchData(_moviesSimilarState) { getMovieSimilarUseCase.invoke(movieId) }
    fun fetchDetailPerson(personId: Int) = fetchData(_personState) { getDetailPerson.invoke(personId) }
    fun fetchMoviesForActor(personId: Int) = fetchData(_moviesForActorState) { getMoviesForActorUseCase.invoke(personId) }

}
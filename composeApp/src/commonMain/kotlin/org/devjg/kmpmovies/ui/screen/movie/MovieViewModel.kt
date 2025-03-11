package org.devjg.kmpmovies.ui.screen.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.model.MovieDetail
import org.devjg.kmpmovies.domain.usecases.GetDetailMovieUseCase
import org.devjg.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import org.devjg.kmpmovies.domain.usecases.GetTopRatedMoviesUseCase

class MovieViewModel(
    private val useCase: GetPopularMoviesUseCase,
    private val useCaseTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val useCaseGetDetailMovie :GetDetailMovieUseCase
) : ViewModel() {

    private val _moviesState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val moviesState: StateFlow<Resource<List<Movie>>> get() = _moviesState

    private val _topRatedMoviesState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val topRatedMoviesState: StateFlow<Resource<List<Movie>>> get() = _topRatedMoviesState

    private val _movieDetailState = MutableStateFlow<Resource<MovieDetail>>(Resource.Loading)
    val movieDetailState: StateFlow<Resource<MovieDetail>> = _movieDetailState

    fun fetchPopularMovies() {
        viewModelScope.launch {
            useCase.invoke().collect { resource ->
                _moviesState.value = resource
            }
        }
    }

    fun fetchTopRatedMovies() {
        viewModelScope.launch {
            useCaseTopRatedMoviesUseCase.invoke().collect { resource ->
                _topRatedMoviesState.value = resource
            }
        }
    }

    fun fetchMovieDetail(movieId: Int) {
        viewModelScope.launch {
            useCaseGetDetailMovie.invoke(movieId).collect { resource ->
                _movieDetailState.value = resource
            }
        }
    }
}
package org.devjg.kmpmovies.ui.screen.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import org.devjg.kmpmovies.domain.usecases.GetTopRatedMoviesUseCase

class MovieViewModel(private val useCase: GetPopularMoviesUseCase,private val useCaseTopRatedMoviesUseCase: GetTopRatedMoviesUseCase):ViewModel() {

    private val _moviesState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val moviesState: StateFlow<Resource<List<Movie>>> get() = _moviesState

    private val _topRatedMoviesState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val topRatedMoviesState: StateFlow<Resource<List<Movie>>> get() = _topRatedMoviesState

    fun fetchPopularMovies(){
        viewModelScope.launch {
            useCase.invoke().collect{ resource ->
                _moviesState.value = resource
            }
        }
    }

    fun fetchTopRatedMovies(){
        viewModelScope.launch {
            useCaseTopRatedMoviesUseCase.invoke().collect{ resource ->
                _topRatedMoviesState.value = resource
            }
        }
    }
}
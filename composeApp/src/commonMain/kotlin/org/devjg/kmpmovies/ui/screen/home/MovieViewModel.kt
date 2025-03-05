package org.devjg.kmpmovies.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.usecases.GetPopularMoviesUseCase

class MovieViewModel(private val useCase: GetPopularMoviesUseCase):ViewModel() {

    private val _moviesState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val moviesState: StateFlow<Resource<List<Movie>>> get() = _moviesState

    fun fetchPopularMovies(){
        viewModelScope.launch {
            useCase.invoke().collect{ resource ->
                _moviesState.value = resource
            }
        }
    }
}
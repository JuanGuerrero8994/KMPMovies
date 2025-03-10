package org.devjg.kmpmovies.ui.screen.tvShowTopRated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.model.TVShow
import org.devjg.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import org.devjg.kmpmovies.domain.usecases.GetTVShowTopRatedUseCase
import org.devjg.kmpmovies.domain.usecases.GetTopRatedMoviesUseCase

class TVShowViewModel(private val useCase: GetTVShowTopRatedUseCase):
    ViewModel() {

    private val _tvShowsState = MutableStateFlow<Resource<List<TVShow>>>(Resource.Loading)
    val tvShowsState: StateFlow<Resource<List<TVShow>>> get() = _tvShowsState

    fun fetchTVShowTopRated(){
        viewModelScope.launch {
            useCase.invoke().collect{ resource ->
                _tvShowsState.value = resource
            }
        }
    }
}
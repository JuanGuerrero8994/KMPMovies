package org.devjg.kmpmovies.ui.screen.movie

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.devjg.kmpmovies.ui.base.ResourceStateHandler
import org.devjg.kmpmovies.ui.components.movie.MovieCarouselView
import org.devjg.kmpmovies.ui.screen.topRatedMovies.TopRatedMoviesView
import org.devjg.kmpmovies.ui.screen.tvShowTopRated.TVShowTopRatedView
import org.devjg.kmpmovies.ui.screen.tvShowTopRated.TVShowViewModel


@Composable
fun MovieScreen(
    movieViewModel: MovieViewModel,
    tvShowViewModel: TVShowViewModel,
    navController: NavController
) {
    val movieState by movieViewModel.moviesState.collectAsState()
    val topRatedState by movieViewModel.topRatedMoviesState.collectAsState()
    val tvShowTopRatedState by tvShowViewModel.tvShowsState.collectAsState() // Aquí se usa tvShowViewModel

    LaunchedEffect(Unit) {
        movieViewModel.fetchPopularMovies()
        movieViewModel.fetchTopRatedMovies()
        tvShowViewModel.fetchTVShowTopRated() // Asegúrate de que este método esté en tvShowViewModel
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            ResourceStateHandler(
                resource = movieState,
                successContent = { movies ->
                    MovieCarouselView(movies = movies, navController)
                }
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            ResourceStateHandler(
                resource = topRatedState,
                successContent = { movies ->
                    TopRatedMoviesView(movies = movies, navController = navController)
                }
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            ResourceStateHandler(
                resource = tvShowTopRatedState,
                successContent = { tvShow ->
                    TVShowTopRatedView(tvShow = tvShow ,navController)
                }
            )
        }

    }

}

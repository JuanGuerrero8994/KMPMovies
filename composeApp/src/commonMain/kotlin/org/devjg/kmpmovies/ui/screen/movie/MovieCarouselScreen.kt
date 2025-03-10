package org.devjg.kmpmovies.ui.screen.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.ui.movie.MovieCarouselView

@Composable
fun MovieCarouselScreen(viewModel: MovieViewModel, navController: NavController) {
    val movieState by viewModel.moviesState.collectAsState()

    LaunchedEffect(Unit) { viewModel.fetchPopularMovies() }


    when (movieState) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            val movies = (movieState as Resource.Success<List<Movie>>).data
            MovieCarouselView(movies = movies)
        }

        is Resource.Error -> {
            Text(
                text = "Error: ${(movieState as Resource.Error).exception.message}",
                color = MaterialTheme.colors.error
            )
        }
    }
}

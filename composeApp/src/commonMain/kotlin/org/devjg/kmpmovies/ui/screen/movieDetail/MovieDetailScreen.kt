package org.devjg.kmpmovies.ui.screen.movieDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import org.devjg.kmpmovies.domain.model.MovieDetail
import org.devjg.kmpmovies.ui.base.ResourceStateHandler
import org.devjg.kmpmovies.ui.components.movie.MovieDetailView
import org.devjg.kmpmovies.ui.screen.movie.MovieViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun MovieDetailScreen(
    movieId: Int,
    movieViewModel: MovieViewModel,
    navController: NavController
) {
    val movieDetailState by movieViewModel.movieDetailState.collectAsState()

    LaunchedEffect(Unit) {
        movieViewModel.fetchMovieDetail(movieId)
    }

    ResourceStateHandler(
        resource = movieDetailState,
        successContent = { movie ->
            MovieDetailView(movie = movie, navController = navController)
        }
    )
}

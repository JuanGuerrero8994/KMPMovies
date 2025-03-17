package org.devjg.kmpmovies.ui.components.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.ui.navigation.Destinations

@Composable
fun MovieCarouselItem(movie: Movie, navController: NavController,alpha:Float) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .alpha(alpha) // Aplicamos la opacidad animada
    ){
        Card(
            modifier = Modifier
                .size(315.dp, 420.dp)
                .align(Alignment.Center)
                .clickable {
                    navigateToMovieDetail(navController, movie)
                }
        ) {
            Image(
                painter = rememberAsyncImagePainter(movie.posterUrl),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


private fun navigateToMovieDetail(navController: NavController ,movie: Movie){
    navController.navigate(Destinations.MovieDetailScreen.createRoute(movie.id)) {
        popUpTo(Destinations.MovieDetailScreen.route) { inclusive = true }
        launchSingleTop = true
    }
}
package org.devjg.kmpmovies.ui.components.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.ui.navigation.Destinations

@Composable
fun MovieCarouselItem(movie: Movie, navController: NavController) {
    Box(modifier = Modifier.padding(8.dp)) {
        
        Image(
            painter = rememberAsyncImagePainter(movie.posterUrl),
            contentDescription = "${movie.title} background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(400.dp)
                .width(1000.dp)
                .blur(16.dp) 
                .alpha(0.3f) 
        )

        
        Card(
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.DarkGray.copy(alpha = 0.8f),
            elevation = 8.dp,
            modifier = Modifier
                .size(180.dp, 270.dp) 
                .align(Alignment.Center)
                .clickable {
                    navController.navigate(Destinations.MovieDetailScreen.createRoute(movie.id)) {
                        popUpTo(Destinations.MovieDetailScreen.route) { inclusive = true }
                        launchSingleTop = true
                    }
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
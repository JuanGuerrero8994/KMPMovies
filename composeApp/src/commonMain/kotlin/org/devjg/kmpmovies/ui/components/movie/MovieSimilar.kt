package org.devjg.kmpmovies.ui.components.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.ui.base.RatingStars
import org.devjg.kmpmovies.ui.navigation.Destinations

@Composable
fun MovieSimilarCard(movie: Movie, navController: NavController) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(250.dp)
            .clickable {
                navController.navigate(Destinations.MovieDetailScreen.createRoute(movie.id)){
                    popUpTo(Destinations.MovieDetailScreen.route) { inclusive = true } // Evita duplicaciones
                    launchSingleTop = true
                }
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterUrl}",
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(movie.title, color = Color.White, fontSize = 15.sp)

            RatingStars(movie.voteAverage ?: 0.0)
        }
    }
}

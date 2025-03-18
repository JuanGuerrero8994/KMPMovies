package org.devjg.kmpmovies.ui.components.movie

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
fun MovieCard(movie: Movie, navController: NavController) {

    val shouldAnimate = remember { true }

    val offset by animateFloatAsState(
        targetValue = if (shouldAnimate) 0f else 1000f,
        animationSpec = tween(durationMillis = 5000)
    )
    Column(modifier = Modifier.width(150.dp)) {
        Card(
            modifier = Modifier
                .width(150.dp)
                .height(250.dp)
                .offset(x = offset.dp)
                .clickable {
                    navController.navigate(Destinations.MovieDetailScreen.createRoute(movie.id)) {
                        popUpTo(Destinations.MovieDetailScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
            shape = RoundedCornerShape(8.dp),
            elevation = 4.dp
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterUrl}",
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 15.sp,
                maxLines = 2,
                lineHeight = 18.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))

            RatingStars(movie.voteAverage ?: 0.0)
        }
    }
}

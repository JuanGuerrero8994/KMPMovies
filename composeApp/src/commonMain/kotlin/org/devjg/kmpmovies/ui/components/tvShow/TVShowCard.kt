package org.devjg.kmpmovies.ui.components.tvShow

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
import org.devjg.kmpmovies.domain.model.TVShow
import org.devjg.kmpmovies.ui.base.RatingStars

@Composable
fun TVShowCard(tvShow: TVShow, navController: NavController) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(250.dp)
            .clickable { },
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        Column {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${tvShow.posterUrl}",
                contentDescription = tvShow.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()

            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(tvShow.title ?: "", color = Color.White, fontSize = 15.sp)

            RatingStars(tvShow.voteAverage ?: 0.0)
        }
    }
}

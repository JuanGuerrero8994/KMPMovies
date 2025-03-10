package org.devjg.kmpmovies.ui.components.tvShow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.model.TVShow

@Composable
fun TVShowCard(tvShow: TVShow, navController: NavController) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(220.dp)
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
        }
    }
}

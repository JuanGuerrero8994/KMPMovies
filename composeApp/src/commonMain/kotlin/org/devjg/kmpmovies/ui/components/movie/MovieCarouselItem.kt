package org.devjg.kmpmovies.ui.components.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import org.devjg.kmpmovies.domain.model.Movie

@Composable
fun MovieCarouselItem(movie: Movie) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 250.dp, height = 350.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = movie.posterUrl),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}
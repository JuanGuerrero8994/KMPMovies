package org.devjg.kmpmovies.ui.components.scaffold.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.devjg.kmpmovies.domain.model.Movie

@Composable
fun MovieCard(movie: Movie, isActive: Boolean) {
    val scale by animateFloatAsState(targetValue = if (isActive) 1.0f else 0.9f)

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .width(150.dp)
            .height(220.dp)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                alpha = if (isActive) 1f else 0.7f
            )
            .then(
                if (!isActive) Modifier.blur(10.dp) else Modifier // Aplicar el desenfoque solo si no es activo
            ),
        elevation = if (isActive) 10.dp else 4.dp
    ) {
        Column {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterUrl}",
                contentDescription = movie.title,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = movie.title,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

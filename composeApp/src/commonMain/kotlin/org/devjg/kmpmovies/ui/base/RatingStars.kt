package org.devjg.kmpmovies.ui.base

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingStars(voteAverage: Double) {
    val maxStars = 5
    val filledStars = (voteAverage / 2).toInt() // Convertir escala de 10 a 5
    val halfStar = (voteAverage % 2) >= 1.0 // Verifica si hay media estrella

    Row {
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Star",
                tint = Color.Yellow,
                modifier = Modifier.size(16.dp)
            )
        }
        if (halfStar) {
            Icon(
                imageVector = Icons.Rounded.Star,
                contentDescription = "Half Star",
                tint = Color.Yellow,
                modifier = Modifier.size(16.dp)
            )
        }
        repeat(maxStars - filledStars - if (halfStar) 1 else 0) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = "Empty Star",
                tint = Color.Yellow,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
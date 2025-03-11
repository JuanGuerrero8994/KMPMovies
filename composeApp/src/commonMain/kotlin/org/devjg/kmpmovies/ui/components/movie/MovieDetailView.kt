package org.devjg.kmpmovies.ui.components.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import org.devjg.kmpmovies.domain.model.MovieDetail

@Composable
fun MovieDetailView(movie: MovieDetail, navController: NavController) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            IconButton(
                onClick = { navController.popBackStack() }, // 🔹 Volver atrás
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
        }

        item {
            Image(
                painter = rememberAsyncImagePainter(model = movie.posterUrl),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f) // 🔹 Mantiene la proporción del póster
            )
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            Text(text = movie.title, style = MaterialTheme.typography.h5, color = Color.White, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Release Date: ${movie.releaseDate}", color = Color.White)
            Text(text = "Vote Average: ${movie.voteAverage}", color = Color.White)
            Text(text = "Overview: ${movie.overview}", color = Color.White)
        }
    }
}

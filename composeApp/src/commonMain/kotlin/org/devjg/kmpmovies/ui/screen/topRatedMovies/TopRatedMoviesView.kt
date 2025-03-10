package org.devjg.kmpmovies.ui.screen.topRatedMovies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.ui.components.movie.MovieCard

@Composable
fun TopRatedMoviesView(movies: List<Movie>, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) { // Asegurar que ocupa el espacio necesario
        Text(
            text = "Top Rated Movies",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(), // Se asegura de ocupar el ancho disponible
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies) { movie ->
                MovieCard(movie = movie, navController)
            }
        }
    }
}
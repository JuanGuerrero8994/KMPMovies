package org.devjg.kmpmovies.ui.components.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import org.devjg.kmpmovies.domain.model.Cast
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.model.MovieDetail

@Composable
fun MovieDetailView(movie: MovieDetail, movieSimilar:List<Movie>, cast: List<Cast>, navController: NavController) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
        }

        item {
            Row {
                Image(
                    painter = rememberAsyncImagePainter(model = movie.backdropUrl),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(500.dp).align(Alignment.CenterVertically)

                )
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.h5,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Release Date: ${movie.releaseDate}", color = Color.White)
            Text(text = "Vote Average: ${movie.voteAverage}", color = Color.White)

            var expanded by remember { mutableStateOf(false) }

            Column(modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = if (expanded) {
                        movie.overview ?: "No description available"
                    } else {
                        "${movie.overview?.take(100)}..."
                    },
                    color = Color.White,
                    style = MaterialTheme.typography.body2,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (expanded) "Show less" else "Show more",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { expanded = !expanded },
                    style = MaterialTheme.typography.body2,

                    )
            }

            Text(
                text = "Budget: ${movie.budget?.let { "$${it}" } ?: "Not available"}",
                color = Color.White,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = "Revenue: ${movie.revenue?.let { "$${it}" } ?: "Not available"}",
                color = Color.White,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nueva sección para el elenco con LazyRow
            Text(
                text = "Cast",
                style = MaterialTheme.typography.h6,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // LazyRow para mostrar los actores
            LazyRow(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Espaciado entre los elementos
            ) {
                items(cast) { cast ->
                    CastCard(cast = cast) // Aquí pasas cada actor a la función CastCard
                }

            }
            Spacer(modifier = Modifier.height(8.dp))

            Text("Similar Movies", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Espaciado entre los elementos
            ) {
                items(movieSimilar) { movie ->
                    MovieSimilarCard(movie = movie,navController) // Aquí pasas cada actor a la función CastCard
                }

            }
        }
    }
}

// androidApp/src/main/java/org/devjg/kmpmovies/ui/components/AutoScrollCarrousel.kt
package org.devjg.kmpmovies.ui.components.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import org.devjg.kmpmovies.domain.model.Movie
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeSource
import org.devjg.kmpmovies.ui.navigation.Destinations


@Composable
fun MovieCarouselView(movies: List<Movie>, navController: NavController) {
    val hazeState = remember { HazeState() }
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(movies) {
        while (true) {
            delay(4000)
            currentIndex = (currentIndex + 1) % movies.size
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 🔹 Fondo desenfocado con overlay oscuro
        if (movies.isNotEmpty()) {
            val imagePainter: Painter = rememberAsyncImagePainter(model = movies[currentIndex].posterUrl)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .hazeSource(state = hazeState) // Aplica desenfoque en el fondo
                    .zIndex(-1f) // 🔹 Fondo detrás del carrusel
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = "Blurred Background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .hazeSource(hazeState) // 🔹 Fuente del desenfoque
                )

                // 🔥 Overlay oscuro para mejorar visibilidad en Dark Theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)) // Oscurece el fondo
                )
            }
        }

        // 🔹 Carrusel de películas con tarjetas ajustadas a Dark Theme
        LazyRow(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            itemsIndexed(movies) { index, movie ->
                if (index == currentIndex) {
                    MovieCarouselItem(movie,navController)
                }
            }
        }
    }
}

@Composable
fun MovieCarouselItem(movie: Movie,navController: NavController) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color.DarkGray.copy(alpha = 0.8f),
        elevation = 8.dp,
        modifier = Modifier.padding(8.dp)
            .clickable {
                navController.navigate(Destinations.MovieDetailScreen.createRoute(movie.id)) {
                    popUpTo(Destinations.MovieDetailScreen.route) { inclusive = true } // Evita duplicaciones
                    launchSingleTop = true // Evita múltiples instancias
                }
        }
    ) {
        Image(
            painter = rememberAsyncImagePainter(movie.posterUrl),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(180.dp, 270.dp)
        )
    }
}

// androidApp/src/main/java/org/devjg/kmpmovies/ui/components/AutoScrollCarrousel.kt
package org.devjg.kmpmovies.ui.components.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import org.devjg.kmpmovies.domain.model.Movie


@Composable
fun MovieCarouselView(movies: List<Movie>, navController: NavController) {
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(movies) {
        while (true) {
            delay(4000)
            currentIndex = (currentIndex + 1) % movies.size
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fondo de la imagen
        if (movies.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(model = movies[currentIndex].posterUrl),
                contentDescription = "Blurred Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // 🔥 Capa de oscurecimiento en la imagen para simular blur
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // Ajusta la transparencia para el efecto
            )
        }

        // Contenido en primer plano
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            LazyRow(
                modifier = Modifier.wrapContentWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                itemsIndexed(movies) { index, movie ->
                    if (index == currentIndex) {
                        MovieCarouselItem(movie)
                    }
                }
            }
        }
    }
}

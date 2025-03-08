// androidApp/src/main/java/org/devjg/kmpmovies/ui/components/AutoScrollCarrousel.kt
package org.devjg.kmpmovies.ui.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import org.devjg.kmpmovies.domain.model.Movie


@Composable
fun MovieCarouselView(movies: List<Movie>) {
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
        if (movies.isNotEmpty()) {
            val imagePainter: Painter = rememberAsyncImagePainter(model = movies[currentIndex].posterUrl)
            Image(
                painter = imagePainter,
                contentDescription = "Blurred Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(20.dp)
            )
        }
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




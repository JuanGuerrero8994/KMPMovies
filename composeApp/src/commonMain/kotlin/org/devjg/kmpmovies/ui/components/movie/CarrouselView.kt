
package org.devjg.kmpmovies.ui.components.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import org.devjg.kmpmovies.domain.model.Movie
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState


@Composable
fun MovieCarouselView(movies: List<Movie>, navController: NavController) {
    var currentIndex by remember { mutableStateOf(0) }
    var isFading by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isFading) 0.3f else 1f,
        animationSpec = tween(1000),
        label = "alphaAnimation"
    )

    LaunchedEffect(movies) {
        while (true) {
            delay(2500)
            isFading = true
            delay(1500)
            currentIndex = (currentIndex + 1) % movies.size
            isFading = false
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            itemsIndexed(movies) { index, movie ->
                if (index == currentIndex) {
                    MovieCarouselItem(
                        movie = movie,
                        navController = navController,
                        alpha = alpha // Pasamos la opacidad animada
                    )
                }
            }
        }
    }
}





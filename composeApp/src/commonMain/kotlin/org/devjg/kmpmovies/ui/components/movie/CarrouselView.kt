
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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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


// androidApp/src/main/java/org/devjg/kmpmovies/ui/components/AutoScrollCarrousel.kt
package org.devjg.kmpmovies.ui.components.scaffold.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.ui.screen.home.MovieViewModel

@Composable
fun CarrouselView(viewModel: MovieViewModel, navController: NavController) {
    val moviesState by viewModel.moviesState.collectAsState()
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.fetchPopularMovies()
    }

    // Temporizador para el deslizamiento automático
    LaunchedEffect(currentIndex) {
        while (true) {
            delay(3000) // Cambiar cada 3 segundos
            currentIndex =
                ((((currentIndex + 1) % (moviesState as Resource.Success).data.size) ?: 1))
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (moviesState) {
            is Resource.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is Resource.Error -> Text(text = "Error al cargar las películas", modifier = Modifier.align(Alignment.Center))
            is Resource.Success -> {
                val movies = (moviesState as Resource.Success).data.orEmpty()
                LazyRow(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(movies) { movie ->
                        MovieCard(movie, isActive = currentIndex == movies.indexOf(movie))
                    }
                }
            }
        }
    }
}



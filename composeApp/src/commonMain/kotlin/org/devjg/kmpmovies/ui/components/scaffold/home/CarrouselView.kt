// androidApp/src/main/java/org/devjg/kmpmovies/ui/components/AutoScrollCarrousel.kt
package org.devjg.kmpmovies.ui.components.scaffold.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.ui.screen.home.MovieViewModel

@Composable
fun CarrouselView(viewModel: MovieViewModel,navController: NavController) {
    val moviesState by viewModel.moviesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPopularMovies()
    }

    when (moviesState) {
        is Resource.Loading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        is Resource.Error -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error al cargar las películas")
        }
        is Resource.Success -> {
            val movies = (moviesState as Resource.Success).data.orEmpty()
            if (movies.isNotEmpty()) {
                AutoScrollCarousel(movies)
            }
        }
    }
}


@Composable
fun AutoScrollCarousel(movies: List<Movie>) {
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2, // Para empezar en el centro del carrusel
        pageCount = { Int.MAX_VALUE } // Scroll infinito
    )

    // Efecto para el auto-scroll
    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 🎬 Fondo borroso con la película actual
        BackgroundBlurImage(imageUrl = movies[pagerState.currentPage % movies.size].posterUrl!!)

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { index ->
                val movie = movies[index % movies.size]
                MovieCard(movie, isActive = (pagerState.currentPage % movies.size) == (index % movies.size))
            }
        }
    }
}



@Composable
fun BackgroundBlurImage(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "Blurred Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .blur(30.dp) // 🎨 Aplica el desenfoque al fondo
            .background(Color.Black.copy(alpha = 0.3f)) // Oscurece un poco la imagen
    )
}
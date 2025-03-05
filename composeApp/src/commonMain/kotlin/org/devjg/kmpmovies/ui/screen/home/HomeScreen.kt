package org.devjg.kmpmovies.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Movie
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MovieViewModel = koinViewModel()
) {
    val moviesState by viewModel.moviesState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPopularMovies()
    }

    when (moviesState) {
        is Resource.Loading -> {
            Text("Cargando....")
        }
        is Resource.Success -> {
            val movies = (moviesState as Resource.Success<List<Movie>>).data
            LazyColumn {
                items(movies) { movie ->
                    MovieItem(movie)
                }
            }
        }
        is Resource.Error -> {
            Text("Error al cargar las películas")
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = movie.title, style = MaterialTheme.typography.h6)
        //Text(text = "Rating: ${movie.voteAverage}")
    }
}

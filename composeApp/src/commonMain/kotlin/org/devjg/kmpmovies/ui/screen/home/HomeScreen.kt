package org.devjg.kmpmovies.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.devjg.kmpmovies.ui.screen.movie.MovieCarouselScreen
import org.devjg.kmpmovies.ui.screen.movie.MovieViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MovieViewModel = koinViewModel()
) {

    MovieCarouselScreen(viewModel,navController)
}




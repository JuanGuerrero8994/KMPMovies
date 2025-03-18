package org.devjg.kmpmovies.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.devjg.kmpmovies.ui.screen.movie.MovieScreen
import org.devjg.kmpmovies.ui.screen.movie.MovieViewModel
import org.devjg.kmpmovies.ui.screen.tvShowTopRated.TVShowViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun HomeScreen(
    navController: NavController,
    movieViewModel: MovieViewModel,
    tvShowViewModel: TVShowViewModel
) {
    MovieScreen(movieViewModel,tvShowViewModel,navController)
}




package org.devjg.kmpmovies.ui.screen.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.devjg.kmpmovies.ui.components.scaffold.home.CarrouselView
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MovieViewModel = koinViewModel()
) {

    CarrouselView(viewModel, navController)
}


package org.devjg.kmpmovies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import org.devjg.kmpmovies.ui.components.scaffold.BottomNavScreen
import org.devjg.kmpmovies.ui.components.scaffold.ScaffoldComponent
import org.devjg.kmpmovies.ui.screen.account.AccountScreen
import org.devjg.kmpmovies.ui.screen.personDetail.PersonDetailScreen
import org.devjg.kmpmovies.ui.screen.favourite.FavouriteScreen
import org.devjg.kmpmovies.ui.screen.home.HomeScreen
import org.devjg.kmpmovies.ui.screen.movie.MovieViewModel
import org.devjg.kmpmovies.ui.screen.movieDetail.MovieDetailScreen
import org.devjg.kmpmovies.ui.screen.tvShowTopRated.TVShowViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun NavGraph(navController: NavHostController) {
    val movieViewModel: MovieViewModel = koinViewModel()
    val tvShowViewModel: TVShowViewModel = koinViewModel()

    ScaffoldComponent(navController, title = "Netflix") {
        NavHost(navController = navController, startDestination = Destinations.HomeScreen.route) {
            // Rutas simples
            addBottomNavRoute(navController, BottomNavScreen.Home.route) { HomeScreen(it,movieViewModel, tvShowViewModel) }
            addBottomNavRoute(navController, BottomNavScreen.Favourite.route) { FavouriteScreen(it) }
            addBottomNavRoute(navController, BottomNavScreen.Account.route) { AccountScreen(it) }

            //Rutas con Argumentos
            addRouteWithArgs(
                navController = navController,
                route = Destinations.MovieDetailScreen.route,
                arguments = listOf(
                    navArgument("movieId") { type = NavType.IntType }
                )
            ) { _, backStackEntry ->

                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                MovieDetailScreen(movieId = movieId,movieViewModel, navController = navController)
            }

            addRouteWithArgs(
                navController = navController,
                route = Destinations.PersonDetailScreen.route,
                arguments = listOf(
                    navArgument("personId") { type = NavType.IntType },
                )
            ) { _, backStackEntry ->

                val personId = backStackEntry.arguments?.getInt("personId") ?: 0

                PersonDetailScreen(
                    personId = personId,
                    navController = navController,
                    movieViewModel = movieViewModel
                )
            }
        }
    }
}

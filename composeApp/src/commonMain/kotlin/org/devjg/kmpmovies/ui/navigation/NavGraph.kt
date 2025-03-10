package org.devjg.kmpmovies.ui.navigation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import org.devjg.kmpmovies.ui.components.scaffold.BottomNavScreen
import org.devjg.kmpmovies.ui.components.scaffold.ScaffoldComponent
import org.devjg.kmpmovies.ui.screen.account.AccountScreen
import org.devjg.kmpmovies.ui.screen.favourite.FavouriteScreen
import org.devjg.kmpmovies.ui.screen.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {

    ScaffoldComponent(navController) {
        NavHost(navController = navController, startDestination = Destinations.HomeScreen.route) {
            // Rutas simples
            addBottomNavRoute(navController, BottomNavScreen.Home.route) { HomeScreen(it) }
            addBottomNavRoute(navController, BottomNavScreen.Favourite.route) {FavouriteScreen(it) }
            addBottomNavRoute(navController, BottomNavScreen.Account.route) { AccountScreen(it) }

        }
    }
}

package org.devjg.kmpmovies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import org.devjg.kmpmovies.ui.components.scaffold.BottomNavScreen
import org.devjg.kmpmovies.ui.screen.account.AccountScreen
import org.devjg.kmpmovies.ui.screen.favourite.FavouriteScreen
import org.devjg.kmpmovies.ui.screen.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {


    NavHost(navController = navController, startDestination = Destinations.HomeScreen.route) {
        // Rutas simples
       // addRoute(navController, Destinations.SplashScreen.route) { SplashScreen(it) }
        addBottomNavRoute(navController, BottomNavScreen.Home.route) { HomeScreen(it) }
        addBottomNavRoute(navController, BottomNavScreen.Favourite.route) { FavouriteScreen(it) }
        addBottomNavRoute(navController, BottomNavScreen.Favourite.route) { AccountScreen(it) }

    }
}

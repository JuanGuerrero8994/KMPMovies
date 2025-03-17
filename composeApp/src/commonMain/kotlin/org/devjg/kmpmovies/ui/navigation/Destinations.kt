package org.devjg.kmpmovies.ui.navigation

sealed class Destinations(val route: String) {

    //SPLASHSCREEN
    data object SplashScreen : Destinations("splashScreen")

    //HOME  SCREEN
    data object HomeScreen : Destinations("homeScreen")

    //FAVOURITE SCREEN
    data object FavouriteScreen : Destinations("favouriteScreen")

    //ACCOUNT SCREEN
    data object AccountScreen : Destinations("accountScreen")

    //MovieDetailScreen
    data object MovieDetailScreen : Destinations("movieDetailScreen/{movieId}"){
        fun createRoute(movieId: Int): String = "movieDetailScreen/$movieId"
    }

    //CastDetailScreen
    data object CastDetailScreen : Destinations("castDetailScreen/{movieId}"){
        fun createRoute(movieId: Int): String = "castDetailScreen/$movieId"
    }


}
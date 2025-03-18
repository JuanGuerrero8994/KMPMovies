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

    //PersonDetailScreen
    data object PersonDetailScreen : Destinations("personDetailScreen/{personId}"){
        fun createRoute(personId: Int): String = "personDetailScreen/$personId"
    }


}
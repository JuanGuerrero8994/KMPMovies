package org.devjg.kmpmovies.ui.components.scaffold

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    data object Home : BottomNavScreen("homeScreen", "Home", Icons.Default.Home)
    data object Favourite : BottomNavScreen("favouriteScreen", "Favourite", Icons.Default.Favorite)
    data object Account : BottomNavScreen("accountScreen", "Account", Icons.Default.AccountCircle)

}
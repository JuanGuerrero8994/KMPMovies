package org.devjg.kmpmovies.ui.screen.account

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.devjg.kmpmovies.ui.components.scaffold.ScaffoldComponent

@Composable
fun AccountScreen(navController: NavController){
    ScaffoldComponent(navController){
        Text("Account Screen")
    }
}
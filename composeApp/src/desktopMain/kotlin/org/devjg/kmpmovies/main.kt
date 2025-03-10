package org.devjg.kmpmovies

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.devjg.kmpmovies.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "KMPMovies",
    ) {
        App()
    }
}
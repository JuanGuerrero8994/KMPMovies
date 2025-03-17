package org.devjg.kmpmovies.ui.screen.castDetail


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import org.devjg.kmpmovies.ui.base.LoadingType
import org.devjg.kmpmovies.ui.base.ResourceStateHandler
import org.devjg.kmpmovies.ui.components.castDetail.CastDetailView
import org.devjg.kmpmovies.ui.screen.movie.MovieViewModel


@Composable
fun CastDetailScreen(navController: NavController, castId: Int,movieViewModel: MovieViewModel) {
    val castDetailState by movieViewModel.castState.collectAsState()

    LaunchedEffect(Unit) {
        movieViewModel.fetchCast(castId)
    }
    ResourceStateHandler(
        resource = castDetailState,
        loadingType = LoadingType.Detail,
        successContent = { cast ->
            CastDetailView(cast = cast, navController = navController)
        }
    )

}

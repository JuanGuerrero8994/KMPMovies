package org.devjg.kmpmovies.ui.screen.topRatedMovies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.ui.components.movie.MovieCard

@Composable
fun TopRatedMoviesView(movies: List<Movie>, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    Column(
        modifier = Modifier.fillMaxSize().clickable {
            coroutineScope.launch {
                scrollState.animateScrollToItem(0)
            }
        },
    ) {
        Text(
            text = "Top Rated Movies",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold

        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state =scrollState
        ) {
            items(movies) { movie ->
                MovieCard(movie = movie, navController)
            }
        }
    }
}
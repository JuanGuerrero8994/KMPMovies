package org.devjg.kmpmovies.ui.components.castDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import org.devjg.kmpmovies.data.core.Constants.NOT_AVAILABLE
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.model.Person
import org.devjg.kmpmovies.ui.base.LoadingType
import org.devjg.kmpmovies.ui.base.ResourceStateHandler
import org.devjg.kmpmovies.ui.components.movie.MovieCard
import org.devjg.kmpmovies.ui.screen.movie.MovieViewModel

@Composable
fun PersonDetailView(
    person: Person,
    movieViewModel: MovieViewModel,
    navController: NavController
) {
    val moviesForActorState by movieViewModel.moviesForActorState.collectAsState()

    LaunchedEffect(Unit) {
        movieViewModel.fetchMoviesForActor(personId = person.id)
    }
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
            }

            item {
                Image(
                    painter = rememberAsyncImagePainter(person.profilePath),
                    contentDescription = person.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Name: ${person.name}", style = MaterialTheme.typography.h5)
                Text(text = "Birthday: ${person.birthday}", style = MaterialTheme.typography.body1)
                Text(
                    text = "Place of Birth: ${person.placeOfBirth}",
                    style = MaterialTheme.typography.body1
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Biography:", style = MaterialTheme.typography.h6)
                Text(text = person.biography ?: NOT_AVAILABLE, style = MaterialTheme.typography.body1)

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(text = "Known For:", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                ResourceStateHandler(
                    resource = moviesForActorState,
                    loadingType = LoadingType.Card,
                    successContent = { moviesForActor ->
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.Bottom // Asegura que los elementos se alineen en la parte inferior

                        ) {
                            items(moviesForActor) {
                                MovieCard(movie = it, navController)
                            }
                        }

                    }
                )
            }
        }
    }
}



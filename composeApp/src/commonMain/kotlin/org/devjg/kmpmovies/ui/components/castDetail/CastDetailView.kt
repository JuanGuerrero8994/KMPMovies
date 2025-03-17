package org.devjg.kmpmovies.ui.components.castDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import org.devjg.kmpmovies.domain.model.Cast

@Composable
fun CastDetailView(cast: Cast, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = rememberAsyncImagePainter(cast.profilePath),
            contentDescription = cast.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

//        Text(text = "Name: ${cast.name}", style = MaterialTheme.typography.h5)
//        Text(
//            text = "Birthday: ${cast.birthday ?: "Not available"}",
//            style = MaterialTheme.typography.body1
//        )
//        Text(
//            text = "Place of Birth: ${cast.placeOfBirth ?: "Not available"}",
//            style = MaterialTheme.typography.body1
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Text(text = "Biography:", style = MaterialTheme.typography.h6)
//        Text(text = cast.biography ?: "Not available", style = MaterialTheme.typography.body1)
    }

}


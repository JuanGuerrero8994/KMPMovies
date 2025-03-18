package org.devjg.kmpmovies.ui.components.movieDetail

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import org.devjg.kmpmovies.domain.model.Cast
import org.devjg.kmpmovies.ui.navigation.Destinations


@Composable
fun CastCard(
    cast: Cast,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(8.dp)
    ) {

        Card(
            shape = CircleShape,
            elevation = 4.dp,
            modifier = Modifier.padding(4.dp)
                .clickable { navigateToCastDetail(navController,cast) }
        ) {

            AsyncImage(
                model = cast.profilePath,
                contentDescription = cast.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
        }


        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = cast.name,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


private fun navigateToCastDetail(navController: NavController,cast: Cast){
    navController.navigate(Destinations.PersonDetailScreen.createRoute(personId = cast.id)){
        popUpTo(Destinations.PersonDetailScreen.route) { inclusive = true }
        launchSingleTop = true
    }
}




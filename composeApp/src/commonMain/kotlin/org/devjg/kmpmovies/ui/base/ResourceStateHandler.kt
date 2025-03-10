package org.devjg.kmpmovies.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.devjg.kmpmovies.data.core.Resource

@Composable
fun <T> ResourceStateHandler(
    resource: Resource<T>,
    loadingContent: @Composable () -> Unit = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    },
    errorContent: @Composable (Throwable) -> Unit = { exception ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Error: ${exception.message}",
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(16.dp)
            )
        }
    },
    successContent: @Composable (T) -> Unit
) {
    when (resource) {
        is Resource.Loading -> loadingContent()
        is Resource.Success -> successContent(resource.data)
        is Resource.Error -> errorContent(resource.exception)
    }
}

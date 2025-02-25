package org.devjg.kmpmovies.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class MovieRequest(
    val query: String,
    val page: Int = 1,
    val language: String = "es-ES"
)
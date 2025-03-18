package org.devjg.kmpmovies.data.model.response.movie

import kotlinx.serialization.Serializable


@Serializable
data class MovieSimilarResponse(
    val results: List<MovieResponse>
)
package org.devjg.kmpmovies.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularMoviesResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<MovieResponse>
)
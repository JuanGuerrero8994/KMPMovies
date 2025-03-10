package org.devjg.kmpmovies.data.model.response.tvShow

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TVShowTopRatedResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<TvShowResponse>
)
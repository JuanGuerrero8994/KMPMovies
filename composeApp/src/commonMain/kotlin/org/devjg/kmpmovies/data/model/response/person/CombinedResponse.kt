package org.devjg.kmpmovies.data.model.response.person

import kotlinx.serialization.Serializable
import org.devjg.kmpmovies.data.model.response.movie.MovieResponse

@Serializable
data class CombinedCreditsResponse(
    val cast: List<MovieResponse>
)
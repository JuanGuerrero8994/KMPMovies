package org.devjg.kmpmovies.data.model.response.credits

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.devjg.kmpmovies.data.model.response.movie.MovieResponse
import org.devjg.kmpmovies.domain.model.Movie

@Serializable
data class CastResponse(
    val id: Int,
    @SerialName("name") val name: String? = null,
    @SerialName("character") val character: String? = null,
    @SerialName("profile_path") val profilePath: String? = null,
)
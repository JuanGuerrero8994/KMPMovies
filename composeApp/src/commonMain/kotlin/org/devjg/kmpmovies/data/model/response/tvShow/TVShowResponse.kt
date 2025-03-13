package org.devjg.kmpmovies.data.model.response.tvShow

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowResponse(
    val id: Int,

    @SerialName("name")
    val title: String,

    @SerialName("overview")
    val overview: String?,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("first_air_date")
    val firstAirDate: String,

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Int,

    @SerialName("popularity")
    val popularity: Double,

    @SerialName("adult")
    val adult: Boolean
)

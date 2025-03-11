package org.devjg.kmpmovies.data.model.response.tvShow

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShowResponse(
    val id: Int,

    @SerialName("name") // Nombre de la propiedad en JSON
    val title: String, // Usamos "title" en el modelo de dominio para mayor claridad

    @SerialName("overview")
    val overview: String?,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("first_air_date")
    val firstAirDate: String, // Fecha de emisión del show

    @SerialName("vote_average")
    val voteAverage: Double,

    @SerialName("vote_count")
    val voteCount: Int,

    @SerialName("popularity")
    val popularity: Double,

    @SerialName("adult")
    val adult: Boolean
)

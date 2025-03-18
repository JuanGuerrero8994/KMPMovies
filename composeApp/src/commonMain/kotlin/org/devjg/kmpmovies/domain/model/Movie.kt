package org.devjg.kmpmovies.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String? = null,
    val posterUrl: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val popularity: Double? = null,
    val adult:Boolean? = null
)

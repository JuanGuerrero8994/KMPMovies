package org.devjg.kmpmovies.domain.model

data class TVShow(
    val id: Int,
    val title: String,
    val overview: String?,
    val posterUrl: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val adult: Boolean
)
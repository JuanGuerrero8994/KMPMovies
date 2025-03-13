package org.devjg.kmpmovies.domain.model



data class MovieDetail(
    val id: Int = 0,
    val title: String? = null,
    val overview: String? = null,
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val popularity: Double? = null,
    val adult: Boolean? = null,
    val collection: Collection? = null,
    val budget: Int? = null,
    val revenue: Int? = null,
    val runtime: Int? = null
) {
    
    data class Collection(
        val id: Int = 0,
        val name: String? = null,
        val posterPath: String? = null,
        val backdropPath: String? = null
    )
}
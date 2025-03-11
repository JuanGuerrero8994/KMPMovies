package org.devjg.kmpmovies.domain.model


data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String?,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val popularity: Double?,
    val adult: Boolean?,
    val collection: Collection?,
    val budget: Int?,
    val revenue: Int?,
    val runtime: Int?
) {
    // Clase anidada para la colección a la que pertenece la película
    data class Collection(
        val id: Int,
        val name: String,
        val posterPath: String?,
        val backdropPath: String?
    )
}

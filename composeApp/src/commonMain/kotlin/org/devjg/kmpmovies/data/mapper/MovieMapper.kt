package org.devjg.kmpmovies.data.mapper

import org.devjg.kmpmovies.data.model.response.movie.MovieResponse
import org.devjg.kmpmovies.domain.model.Movie

object MovieMapper {
    private fun toDomain(movieResponse: MovieResponse): Movie {
        return Movie(
            id = movieResponse.id,
            title = movieResponse.title,
            overview = movieResponse.overview,
            posterUrl = "https://image.tmdb.org/t/p/w500${movieResponse.posterPath}",
            releaseDate = movieResponse.releaseDate,
            voteAverage = movieResponse.voteAverage,
            voteCount = movieResponse.voteCount,
            popularity = movieResponse.popularity,
            adult = movieResponse.adult
        )
    }

    fun toDomainList(movieResponseList: List<MovieResponse>): List<Movie> = movieResponseList.map { toDomain(it) }

}
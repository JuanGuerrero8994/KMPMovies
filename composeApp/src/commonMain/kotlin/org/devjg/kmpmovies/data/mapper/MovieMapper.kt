package org.devjg.kmpmovies.data.mapper

import org.devjg.kmpmovies.data.model.response.movie.MovieDetailResponse
import org.devjg.kmpmovies.data.model.response.movie.MovieResponse
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.model.MovieDetail

object MovieMapper {
     fun toDomain(movieResponse: MovieResponse): Movie {
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


    fun toDomainDetail(movieDetailResponse: MovieDetailResponse): MovieDetail {
        return MovieDetail(
            id = movieDetailResponse.id,
            title = movieDetailResponse.title,
            overview = movieDetailResponse.overview,
            posterUrl = "https://image.tmdb.org/t/p/w500${movieDetailResponse.posterPath}",
            backdropUrl = "https://image.tmdb.org/t/p/w500${movieDetailResponse.backdropPath}",
            releaseDate = movieDetailResponse.releaseDate,
            voteAverage = movieDetailResponse.voteAverage,
            voteCount = movieDetailResponse.voteCount,
            popularity = movieDetailResponse.popularity,
            adult = movieDetailResponse.adult,
            collection = movieDetailResponse.belongsToCollection?.let {
                MovieDetail.Collection(
                    id = it.id,
                    name = it.name,
                    posterPath = "https://image.tmdb.org/t/p/w500${it.posterPath}",
                    backdropPath = "https://image.tmdb.org/t/p/w500${it.backdropPath}"
                )
            },
            budget = movieDetailResponse.budget,
            revenue = movieDetailResponse.revenue,
            runtime = movieDetailResponse.runtime
        )
    }
}
package org.devjg.kmpmovies.data.mapper


import org.devjg.kmpmovies.data.model.response.tvShow.TvShowResponse
import org.devjg.kmpmovies.domain.model.TVShow

object TVShowMapper {
    private fun toDomain(tvShowResponse: TvShowResponse): TVShow {
        return TVShow(
            id = tvShowResponse.id,
            title = tvShowResponse.title, // El título puede estar en 'name' para TV Shows
            overview = tvShowResponse.overview,
            posterUrl = "https://image.tmdb.org/t/p/w500${tvShowResponse.posterPath}",
            releaseDate = tvShowResponse.firstAirDate, // 'firstAirDate' para TV Shows
            voteAverage = tvShowResponse.voteAverage,
            voteCount = tvShowResponse.voteCount,
            popularity = tvShowResponse.popularity,
            adult = tvShowResponse.adult
        )
    }

    fun toDomainList(tvShowResponseList: List<TvShowResponse>): List<TVShow> = tvShowResponseList.map { toDomain(it) }
}

package org.devjg.kmpmovies.data.mapper

import org.devjg.kmpmovies.data.model.response.credits.CastResponse
import org.devjg.kmpmovies.domain.model.Cast
import org.devjg.kmpmovies.domain.model.Movie

object CastMapper {
    fun toDomain(castResponse: CastResponse): Cast {
        return Cast(
            id = castResponse.id,
            name = castResponse.name,
            character = castResponse.character,
            profilePath = castResponse.profilePath?.let {
                "https://image.tmdb.org/t/p/w500$it"
            } ?: "https://via.placeholder.com/500x750",
           /* birthday = castResponse.birthday,
            placeOfBirth = castResponse.placeOfBirth,
            biography = castResponse.biography,
            knownFor = castResponse.knownFor.map { movieResponse ->
                Movie(
                    id = movieResponse.id,
                    title = movieResponse.title,
                    overview = movieResponse.overview,
                    posterUrl = movieResponse.posterPath?.let {
                        "https://image.tmdb.org/t/p/w500$it"
                    } ?: "https://via.placeholder.com/500x750"
                )
            },
           adult = castResponse.adult,*/
        )
    }

    fun toDomainList(castResponseList: List<CastResponse>): List<Cast> = castResponseList.map { toDomain(it) }
}
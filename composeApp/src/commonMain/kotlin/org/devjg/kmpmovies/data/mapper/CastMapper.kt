package org.devjg.kmpmovies.data.mapper

import org.devjg.kmpmovies.data.model.response.credits.CastResponse
import org.devjg.kmpmovies.domain.model.Cast

object CastMapper {
    private fun toDomain(castResponse: CastResponse): Cast {
        return Cast(
            id = castResponse.id,
            name = castResponse.name,
            character = castResponse.character,
            profilePath = castResponse.profilePath?.let {
                "https://image.tmdb.org/t/p/w500$it"
            } ?: "https://via.placeholder.com/500x750"
        )
    }

    fun toDomainList(castResponseList: List<CastResponse>): List<Cast> = castResponseList.map { toDomain(it) }
}
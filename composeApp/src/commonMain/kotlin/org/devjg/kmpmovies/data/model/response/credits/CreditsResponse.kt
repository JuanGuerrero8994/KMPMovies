package org.devjg.kmpmovies.data.model.response.credits

import kotlinx.serialization.Serializable

@Serializable
data class CreditsResponse(
    val cast: List<CastResponse>
)

package org.devjg.kmpmovies.data.model.response.credits

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastResponse(
    val id: Int,
    @SerialName("name") val name: String,
    @SerialName("character") val character: String,
    @SerialName("profile_path") val profilePath: String? = null
)
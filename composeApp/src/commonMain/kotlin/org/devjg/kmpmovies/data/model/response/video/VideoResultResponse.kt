package org.devjg.kmpmovies.data.model.response.video

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoResultResponse(
    @SerialName("id") val id: String,
    @SerialName("key") val key: String,
    @SerialName("name") val name: String?,
    @SerialName("site") val site: String?,  // Ejemplo: "YouTube"
    @SerialName("type") val type: String?,  // Ejemplo: "Trailer"
    @SerialName("official") val official: Boolean?,
    @SerialName("published_at") val publishedAt: String?
)
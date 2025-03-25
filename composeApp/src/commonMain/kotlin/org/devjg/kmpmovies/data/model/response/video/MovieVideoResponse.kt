package org.devjg.kmpmovies.data.model.response.video

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideoResponse(
    @SerialName("id") val id: Int,
    @SerialName("results") val results: List<VideoResultResponse>
)

package org.devjg.kmpmovies.data.model.response.person

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.devjg.kmpmovies.data.model.response.credits.CreditsResponse

@Serializable
data class PersonResponse(
    val id: Int,
    @SerialName("name") val name: String? = null,
    @SerialName("birthday") val birthday: String? = null,
    @SerialName("place_of_birth") val placeOfBirth: String? = null,
    @SerialName("biography") val biography: String? = null,
    @SerialName("profile_path") val profilePath: String? = null,
    @SerialName("combined_credits") val combinedCredits: CombinedCreditsResponse?=null
)
package org.devjg.kmpmovies.data.model.response.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.devjg.kmpmovies.data.model.response.video.MovieVideoResponse

@Serializable
data class MovieDetailResponse(
    @SerialName("adult") val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("belongs_to_collection") val belongsToCollection: Collection? = null,
    @SerialName("budget") val budget: Int,
    @SerialName("genres") val genres: List<Genre>,
    @SerialName("homepage") val homepage: String?,
    @SerialName("id") val id: Int,
    @SerialName("imdb_id") val imdbId: String?,
    @SerialName("original_language") val originalLanguage: String,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("overview") val overview: String?,
    @SerialName("popularity") val popularity: Double,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("production_companies") val productionCompanies: List<ProductionCompany>,
    @SerialName("production_countries") val productionCountries: List<ProductionCountry>,
    @SerialName("release_date") val releaseDate: String?,
    @SerialName("revenue") val revenue: Int,
    @SerialName("runtime") val runtime: Int?,
    @SerialName("spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    @SerialName("status") val status: String,
    @SerialName("tagline") val tagline: String?,
    @SerialName("title") val title: String,
    @SerialName("video") val video: Boolean,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("videos") var videos: MovieVideoResponse? = null

)

@Serializable
data class Genre(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)

@Serializable
data class ProductionCompany(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)

@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1") val iso3166_1: String,
    @SerialName("name") val name: String
)

@Serializable
data class SpokenLanguage(
    @SerialName("iso_639_1") val iso639_1: String,
    @SerialName("name") val name: String
)

@Serializable
data class Collection(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?
)

package org.devjg.kmpmovies.data.model.response.credits

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.devjg.kmpmovies.data.model.response.movie.MovieResponse
import org.devjg.kmpmovies.domain.model.Movie

@Serializable
data class CastResponse(
    val id: Int, // ID del actor
    @SerialName("name") val name: String, // Nombre del actor
    @SerialName("character") val character: String, // Personaje interpretado por el actor
    @SerialName("profile_path") val profilePath: String? = null, // Foto de perfil del actor
    //@SerialName("birthday") val birthday: String?, // Fecha de nacimiento
   // @SerialName("place_of_birth") val placeOfBirth: String?, // Lugar de nacimiento
  //  @SerialName("biography") val biography: String?, // Biografía del actor
  //  @SerialName("known_for") val knownFor: List<MovieResponse>, // Películas más conocidas en las que ha trabajado
 //   @SerialName("adult") val adult: Boolean // Si el actor es considerado adulto
)
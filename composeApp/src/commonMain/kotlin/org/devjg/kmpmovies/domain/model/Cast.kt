package org.devjg.kmpmovies.domain.model

data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath:String,
   // val birthday: String?, // Fecha de nacimiento
   // val placeOfBirth: String?, // Lugar de nacimiento
   // val biography: String?, // Biografía del actor
    //val knownFor: List<Movie>, // Películas más conocidas
    //val adult: Boolean // Si el actor es considerado adulto
)
package org.devjg.kmpmovies.domain.model

data class Person(
    val id: Int,
    val name: String,
    val birthday: String,
    val placeOfBirth: String,
    val biography: String,
    val profilePath: String,
    val knownFor: List<Movie>
)
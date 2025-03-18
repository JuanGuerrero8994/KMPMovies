package org.devjg.kmpmovies.domain.usecases

import org.devjg.kmpmovies.domain.repository.MovieRepository

class GetDetailPersonUseCase(private val repository: MovieRepository) {
    suspend operator fun invoke(personId:Int) = repository.getPersonDetails(personId)
}
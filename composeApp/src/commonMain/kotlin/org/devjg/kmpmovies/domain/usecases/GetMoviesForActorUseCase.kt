package org.devjg.kmpmovies.domain.usecases

import org.devjg.kmpmovies.domain.repository.MovieRepository

class GetMoviesForActorUseCase(private val repository: MovieRepository) {
    suspend fun invoke(personId:Int) = repository.getMoviesForActor(personId)
}
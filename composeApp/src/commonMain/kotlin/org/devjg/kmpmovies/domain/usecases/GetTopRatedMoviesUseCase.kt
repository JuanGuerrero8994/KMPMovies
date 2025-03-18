package org.devjg.kmpmovies.domain.usecases

import kotlinx.coroutines.flow.Flow
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.repository.MovieRepository

class GetTopRatedMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Movie>>> = movieRepository.getTopRatedMovies()

}
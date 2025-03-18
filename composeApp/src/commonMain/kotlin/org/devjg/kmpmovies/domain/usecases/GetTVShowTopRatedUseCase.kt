package org.devjg.kmpmovies.domain.usecases

import kotlinx.coroutines.flow.Flow
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.model.TVShow
import org.devjg.kmpmovies.domain.repository.MovieRepository
import org.devjg.kmpmovies.domain.repository.TVShowRepository

class GetTVShowTopRatedUseCase(
    private val tvRepository: TVShowRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<TVShow>>> = tvRepository.getTVShowTopRated()

}
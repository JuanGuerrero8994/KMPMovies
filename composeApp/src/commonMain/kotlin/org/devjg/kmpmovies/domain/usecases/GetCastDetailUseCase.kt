package org.devjg.kmpmovies.domain.usecases

import kotlinx.coroutines.flow.Flow
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Cast
import org.devjg.kmpmovies.domain.repository.MovieRepository

class GetCastDetailUseCase (
    private val repository: MovieRepository
){
    suspend operator fun invoke (movieId:Int) : Flow<Resource<Cast>> = repository.getCastDetail(movieId = movieId)
}
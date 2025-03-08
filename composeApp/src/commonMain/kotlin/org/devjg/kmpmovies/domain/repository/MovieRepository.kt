package org.devjg.kmpmovies.domain.repository

import kotlinx.coroutines.flow.Flow
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Movie

interface MovieRepository {
    suspend fun getPopularMovies(): Flow<Resource<List<Movie>>>
}
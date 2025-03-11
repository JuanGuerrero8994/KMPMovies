package org.devjg.kmpmovies.domain.repository

import kotlinx.coroutines.flow.Flow
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.model.TVShow

interface TVShowRepository {
    suspend fun getTVShowTopRated(): Flow<Resource<List<TVShow>>>
}
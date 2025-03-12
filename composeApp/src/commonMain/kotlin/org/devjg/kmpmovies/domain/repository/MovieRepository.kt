package org.devjg.kmpmovies.domain.repository

import kotlinx.coroutines.flow.Flow
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.domain.model.Cast
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.model.MovieDetail

interface MovieRepository {
    suspend fun getPopularMovies(): Flow<Resource<List<Movie>>>
    suspend fun getTopRatedMovies():Flow<Resource<List<Movie>>>
    suspend fun getDetailMovie(movieId:Int):Flow<Resource<MovieDetail>>
    suspend fun getMovieCast(movieId: Int): Flow<Resource<List<Cast>>>
    suspend fun getMovieSimilar(movieId: Int):Flow<Resource<List<Movie>>>
}
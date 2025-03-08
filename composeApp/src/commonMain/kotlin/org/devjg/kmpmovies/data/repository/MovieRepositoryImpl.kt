package org.devjg.kmpmovies.data.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.data.mapper.MovieMapper
import org.devjg.kmpmovies.data.model.response.PopularMoviesResponse
import org.devjg.kmpmovies.data.remote.Endpoints
import org.devjg.kmpmovies.data.remote.TMDBApi
import org.devjg.kmpmovies.data.remote.buildUrl
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: TMDBApi
) : MovieRepository {
    override suspend fun getPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)
        try {
            // Fetch the full response with the results
            val response: PopularMoviesResponse =
                api.httpClient.get { buildUrl(endpoint = Endpoints.POPULAR_MOVIES) }.body()

            val movieMapper = MovieMapper.toDomainList(response.results)

            emit(Resource.Success(movieMapper))

        } catch (e: JsonConvertException) {
            emit(Resource.Error(Exception("JSON conversion error: ${e.message}")))
        } catch (e: Exception) {
            emit(Resource.Error(Exception("Error fetching movies: ${e.message}")))
        }

    }
}
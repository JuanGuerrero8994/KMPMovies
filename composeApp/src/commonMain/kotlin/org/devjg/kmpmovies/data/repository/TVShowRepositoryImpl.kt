package org.devjg.kmpmovies.data.repository

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.data.mapper.TVShowMapper
import org.devjg.kmpmovies.data.model.response.tvShow.TVShowTopRatedResponse
import org.devjg.kmpmovies.data.model.response.tvShow.TvShowResponse
import org.devjg.kmpmovies.data.remote.Endpoints
import org.devjg.kmpmovies.data.remote.TMDBApi
import org.devjg.kmpmovies.data.remote.buildUrl
import org.devjg.kmpmovies.domain.model.TVShow
import org.devjg.kmpmovies.domain.repository.TVShowRepository

class TVShowRepositoryImpl(
    private val api: TMDBApi
) : TVShowRepository {
    override suspend fun getTVShowTopRated(): Flow<Resource<List<TVShow>>> = flow{
        emit(Resource.Loading)
        try {
            // Fetch the full response with the results
            val response: TVShowTopRatedResponse = api.httpClient.get { buildUrl(endpoint = Endpoints.TOP_RATED_TV_SHOW) }.body()

            val tvMapper = TVShowMapper.toDomainList(response.results)

            emit(Resource.Success(tvMapper))

        } catch (e: JsonConvertException) {
            emit(Resource.Error(Exception("JSON conversion error: ${e.message}")))
        } catch (e: Exception) {
            emit(Resource.Error(Exception("Error fetching tv: ${e.message}")))
        }
    }
}
package org.devjg.kmpmovies.data.repository

import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.devjg.kmpmovies.data.core.Resource
import org.devjg.kmpmovies.data.mapper.CastMapper
import org.devjg.kmpmovies.data.mapper.MovieMapper
import org.devjg.kmpmovies.data.mapper.PersonMapper
import org.devjg.kmpmovies.data.model.response.credits.CreditsResponse
import org.devjg.kmpmovies.data.model.response.movie.MovieDetailResponse
import org.devjg.kmpmovies.data.model.response.movie.MovieForActorResponse
import org.devjg.kmpmovies.data.model.response.movie.MovieSimilarResponse
import org.devjg.kmpmovies.data.model.response.movie.PopularMoviesResponse
import org.devjg.kmpmovies.data.model.response.movie.TopRatedMoviesResponse
import org.devjg.kmpmovies.data.model.response.person.PersonResponse
import org.devjg.kmpmovies.data.model.response.video.MovieVideoResponse
import org.devjg.kmpmovies.data.remote.Endpoints
import org.devjg.kmpmovies.data.remote.TMDBApi
import org.devjg.kmpmovies.data.remote.buildUrl
import org.devjg.kmpmovies.domain.model.Cast
import org.devjg.kmpmovies.domain.model.Movie
import org.devjg.kmpmovies.domain.model.MovieDetail
import org.devjg.kmpmovies.domain.model.Person
import org.devjg.kmpmovies.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: TMDBApi
) : MovieRepository {
    override suspend fun getPopularMovies(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)
        try {
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

    override suspend fun getTopRatedMovies(): Flow<Resource<List<Movie>>> = flow {
        try {
            val response: TopRatedMoviesResponse =
                api.httpClient.get { buildUrl(endpoint = Endpoints.TOP_RATED_MOVIES) }.body()

            val movieMapper = MovieMapper.toDomainList(response.results)

            emit(Resource.Success(movieMapper))

        } catch (e: JsonConvertException) {
            emit(Resource.Error(Exception("JSON conversion error: ${e.message}")))
        } catch (e: Exception) {
            emit(Resource.Error(Exception("Error fetching movies: ${e.message}")))
        }
    }

    override suspend fun getDetailMovie(movieId: Int): Flow<Resource<MovieDetail>> = flow {
        try {
            val response: MovieDetailResponse = api.httpClient.get {
                buildUrl(
                    Endpoints.MOVIE_DETAIL.replace(
                        "{movie_id}",
                        movieId.toString()
                    )
                )
            }.body()

            val videoResponse: MovieVideoResponse = api.httpClient.get {
                buildUrl(
                    Endpoints.VIDEO_TRAILER.replace(
                        "{movie_id}",
                        movieId.toString()
                    )
                )
            }.body()

            val trailerUrl = videoResponse.results
                .firstOrNull { it.type == "Trailer" && it.site == "YouTube" }?.let {
                    "https://www.youtube.com/embed/${it.key}"
                }


            val movieDetail = MovieMapper.toDomainDetail(response, trailerUrl)

            emit(Resource.Success(movieDetail))

        } catch (e: JsonConvertException) {
            emit(Resource.Error(Exception("JSON conversion error: ${e.message}")))
        } catch (e: Exception) {
            emit(Resource.Error(Exception("Error fetching movies: ${e.message}")))

        }
    }

    override suspend fun getMovieSimilar(movieId: Int): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)
        try {
            val response: MovieSimilarResponse = api.httpClient.get {
                buildUrl(Endpoints.MOVIE_SIMILAR.replace("{movie_id}", movieId.toString()))
            }.body()

            val movieMapper = MovieMapper.toDomainList(response.results)

            emit(Resource.Success(movieMapper))
        } catch (e: JsonConvertException) {
            emit(Resource.Error(Exception("JSON conversion error: ${e.message}")))
        } catch (e: Exception) {
            emit(Resource.Error(Exception("Error fetching similar movies: ${e.message}")))
        }
    }

    override suspend fun getMovieCast(movieId: Int): Flow<Resource<List<Cast>>> = flow {
        try {
            emit(Resource.Loading)

            val response: CreditsResponse = api.httpClient.get {
                buildUrl(
                    Endpoints.MOVIE_CREDITS.replace(
                        "{movie_id}",
                        movieId.toString()
                    )
                )
            }.body()

            val castMapper = CastMapper.toDomainList(response.cast)

            emit(Resource.Success(castMapper))


        } catch (e: JsonConvertException) {
            emit(Resource.Error(Exception("JSON conversion error: ${e.message}")))
        } catch (e: Exception) {
            emit(Resource.Error(Exception("Error fetching cast: ${e.message}")))
        }
    }



    override suspend fun getPersonDetails(personId: Int): Flow<Resource<Person>> = flow {
        try {
            emit(Resource.Loading)

            val response: PersonResponse = api.httpClient.get {
                buildUrl(Endpoints.PERSON_DETAIL.replace("{person_id}", personId.toString()))
            }.body()

            Napier.d { "Respuesta PERSON DETAILS :${response} "}

            val person = PersonMapper.toDomain(response)


            emit(Resource.Success(person))
        } catch (e: JsonConvertException) {
            emit(Resource.Error(Exception("JSON conversion error: ${e.message}")))
        } catch (e: Exception) {
            emit(Resource.Error(Exception("Error fetching person details: ${e.message}")))
        }
    }

    override suspend fun getMoviesForActor(personId: Int): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)
        try {
            val response:MovieForActorResponse = api.httpClient.get {
                buildUrl(Endpoints.PERSON_MOVIE_CREDITS.replace("{person_id}", personId.toString()))
            }.body()

            val movieMapper = MovieMapper.toDomainMoviesForActor(response.cast ?: emptyList())

            emit(Resource.Success(movieMapper))
        } catch (e: JsonConvertException) {
            emit(Resource.Error(Exception("JSON conversion error: ${e.message}")))
        } catch (e: Exception) {
            emit(Resource.Error(Exception("Error fetching similar movies: ${e.message}")))
        }
    }
}
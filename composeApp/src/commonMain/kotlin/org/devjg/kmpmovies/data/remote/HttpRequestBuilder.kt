package org.devjg.kmpmovies.data.remote

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.takeFrom
import org.devjg.kmpmovies.BuildKonfig
import org.devjg.kmpmovies.data.core.Constants

fun HttpRequestBuilder.buildUrl(endpoint: String, queryParams: Map<String, String> = emptyMap()) {
    url {
        takeFrom(Endpoints.BASE_URL)
        appendPathSegments(endpoint)
        parameters.append(Constants.API_KEY, BuildKonfig.TMDB_API_KEY)
        queryParams.forEach { (key, value) ->
            parameters.append(key, value)
        }
    }
    accept(ContentType.Application.Json)
}




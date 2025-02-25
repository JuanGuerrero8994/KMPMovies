package org.devjg.kmpmovies.data.remote

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.path
import io.ktor.http.takeFrom

fun HttpRequestBuilder.buildUrl(endpoint: String, queryParams: Map<String, String> = emptyMap()) {
    url {
        takeFrom(Config.BASE_URL)  // Asegúrate de que Config.BASE_URL solo contenga la base URL, es decir, "https://api.themoviedb.org/3"
        appendPathSegments(endpoint)  // Esto añadirá correctamente el endpoint, como "movie/popular"
        parameters.append("api_key", Config.API_KEY)
        queryParams.forEach { (key, value) ->  // Añade parámetros adicionales si es necesario
            parameters.append(key, value)
        }
    }
    accept(ContentType.Application.Json)
}
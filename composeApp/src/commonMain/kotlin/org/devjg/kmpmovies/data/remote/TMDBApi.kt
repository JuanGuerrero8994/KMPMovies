package org.devjg.kmpmovies.data.remote

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import org.devjg.kmpmovies.createHttpClient

class TMDBApi {
    val httpClient: HttpClient by lazy { createHttpClient() }

    init {
        Napier.base(DebugAntilog())
    }
}
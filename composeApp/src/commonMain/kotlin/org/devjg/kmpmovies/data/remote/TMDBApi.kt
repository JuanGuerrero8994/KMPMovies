package org.devjg.kmpmovies.data.remote

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.ProxyBuilder
import io.ktor.client.engine.http
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.devjg.kmpmovies.createHttpClient

class TMDBApi {
    val httpClient: HttpClient by lazy { createHttpClient() }

    init {
        Napier.base(DebugAntilog())
    }
    /*val httpClient by lazy {
        HttpClient() {
            engine {
              // proxy = ProxyBuilder.http("http://proxy.jus.gov.ar:8080")
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.DEFAULT
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.v(tag = "HttpClient", message = message)
                    }
                }
            }
        }
    }


    init {
        Napier.base(DebugAntilog())
    }*/
}
package org.devjg.kmpmovies

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


package org.devjg.kmpmovies.data.remote

object Endpoints {


    const val BASE_URL = "https://api.themoviedb.org/3"

    // MOVIES

    const val POPULAR_MOVIES = "movie/popular"

    const val TOP_RATED_MOVIES ="movie/top_rated"

    const val MOVIE_DETAIL = "/movie/{movie_id}"

    const val MOVIE_CREDITS  ="/movie/{movie_id}/credits"

    const val MOVIE_SIMILAR = "/movie/{movie_id}/similar"


    // TV or SERIES

    const val TOP_RATED_TV_SHOW ="tv/top_rated"

    const val TV_DETAIL = "/tv/{tv_id}"

    const val TV_SHOW_CREDITS = "/tv/{tv_id}/credits"

}

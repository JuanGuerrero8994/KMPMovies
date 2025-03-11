package org.devjg.kmpmovies.di

import io.ktor.client.HttpClient
import org.devjg.kmpmovies.data.remote.TMDBApi
import org.devjg.kmpmovies.data.repository.MovieRepositoryImpl
import org.devjg.kmpmovies.data.repository.TVShowRepositoryImpl
import org.devjg.kmpmovies.domain.repository.MovieRepository
import org.devjg.kmpmovies.domain.repository.TVShowRepository
import org.devjg.kmpmovies.domain.usecases.GetDetailMovieUseCase
import org.devjg.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import org.devjg.kmpmovies.domain.usecases.GetTVShowTopRatedUseCase
import org.devjg.kmpmovies.domain.usecases.GetTopRatedMoviesUseCase
import org.devjg.kmpmovies.ui.screen.movie.MovieViewModel
import org.devjg.kmpmovies.ui.screen.tvShowTopRated.TVShowViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val dataModule = module {
    single { HttpClient() }
    single { TMDBApi() }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    single<TVShowRepository> { TVShowRepositoryImpl(get()) }

}

private val domainModule = module {
    factory { GetPopularMoviesUseCase(get()) }
    factory { GetTopRatedMoviesUseCase(get()) }
    factory { GetTVShowTopRatedUseCase(get()) }
    factory { GetDetailMovieUseCase(get()) }

}


private val viewModelModule = module {
    viewModel { MovieViewModel(get(),get(),get()) }
    viewModel { TVShowViewModel(get()) }
}


var sharedModules = listOf(domainModule, dataModule, viewModelModule)


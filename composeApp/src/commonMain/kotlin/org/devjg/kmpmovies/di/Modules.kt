package org.devjg.kmpmovies.di

import io.ktor.client.HttpClient
import org.devjg.kmpmovies.data.remote.TMDBApi
import org.devjg.kmpmovies.data.repository.MovieRepositoryImpl
import org.devjg.kmpmovies.domain.repository.MovieRepository
import org.devjg.kmpmovies.domain.usecases.GetPopularMoviesUseCase
import org.devjg.kmpmovies.domain.usecases.GetTopRatedMoviesUseCase
import org.devjg.kmpmovies.ui.screen.movie.MovieViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val dataModule = module {
    single { HttpClient() }
    single { TMDBApi() }
    single<MovieRepository> { MovieRepositoryImpl(get()) }
}

private val domainModule = module {
    factory { GetPopularMoviesUseCase(get()) }
    factory { GetTopRatedMoviesUseCase(get()) }

}


private val viewModelModule = module {
    viewModel { MovieViewModel(get(),get()) }
}


var sharedModules = listOf(domainModule, dataModule, viewModelModule)


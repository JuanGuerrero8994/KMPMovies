package org.devjg.kmpmovies

import android.app.Application
import org.devjg.kmpmovies.di.initKoin

class TMDBApp:Application() {
    override fun onCreate() {
        initKoin()
        super.onCreate()
    }
}
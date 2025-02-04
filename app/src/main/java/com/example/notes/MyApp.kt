package com.example.notes

import android.app.Application
import com.example.notes.data.AppContainer

class MyApp : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
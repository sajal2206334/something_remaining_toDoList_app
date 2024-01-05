package com.example.somethingremaining

import android.app.Application
import com.example.somethingremaining.data.AppContainer
import com.example.somethingremaining.data.AppDataContainer

class SomethingRemianingApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
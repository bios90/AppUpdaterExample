package com.example.appupdaterexample.common

import android.app.Application

class AppClass : Application() {
    companion object {
        lateinit var app: AppClass
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}
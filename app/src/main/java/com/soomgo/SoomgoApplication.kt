package com.soomgo

import android.app.Application
import com.soomgo.myapplication.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SoomgoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(applicationModule)
        }
    }
}
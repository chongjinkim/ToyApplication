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


abstract class Engine{
    abstract fun startEngine()
}

class ElectronicEngine() : Engine(){
    override fun startEnging(){
        //E Engine
    }
}

class DiselEngine : Engine(){
    override fun startEngine() {
        //start disel engine
    }

}

class Car(){
    private val engine = Engine()

    fun start(){
        engine.startEngine()
    }
}

class NewCar(val engine : Engine){
    fun startCar(){
        engine.startEngine()
    }
}
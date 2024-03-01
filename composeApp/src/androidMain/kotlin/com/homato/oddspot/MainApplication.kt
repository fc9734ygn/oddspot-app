package com.homato.oddspot

import DriverFactory
import LocationProvider
import android.app.Application
import appContext
import createDatabase
import di.DataModule
import di.DomainModule
import di.UiModule
import di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.module

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()

            modules(
                UiModule().module,
                DomainModule().module,
                DataModule().module,
                networkModule,
                module {
                    single { DriverFactory(androidContext()) }
                    single { createDatabase(get()) }
                    single { LocationProvider() }
                }
            )
        }
        appContext = applicationContext
    }
}
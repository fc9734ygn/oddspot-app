package com.homato.oddspot

import DriverFactory
import android.app.Application
import createDatabase
import di.SharedModule
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
                SharedModule().module,
                networkModule,
                module {
                    single { DriverFactory(androidContext()) }
                    single { createDatabase(get()) }
                }
            )
        }
    }
}
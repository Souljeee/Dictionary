package com.soulje.dictionary.app

import android.app.Application
import com.soulje.dictionary.model.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TranslatorApp)
            modules(appModule)
        }
    }
}
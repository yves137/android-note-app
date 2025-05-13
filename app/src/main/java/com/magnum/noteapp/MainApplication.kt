package com.magnum.noteapp

import android.app.Application
import com.magnum.noteapp.data.di.databaseModule
import com.magnum.noteapp.data.di.noteRepositoryImplModule
import com.magnum.noteapp.presentation.di.notesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(databaseModule, notesModule, noteRepositoryImplModule)
        }
    }
}
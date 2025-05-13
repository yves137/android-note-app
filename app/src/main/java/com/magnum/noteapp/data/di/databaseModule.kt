package com.magnum.noteapp.data.di

import com.magnum.noteapp.data.database.ApplicationDb
import org.koin.dsl.module

val databaseModule= module {
    single { ApplicationDb(get()) }
}
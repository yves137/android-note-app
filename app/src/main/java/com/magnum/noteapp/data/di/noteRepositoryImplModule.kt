package com.magnum.noteapp.data.di

import com.magnum.noteapp.data.repository.NoteRepositoryImpl
import com.magnum.noteapp.domain.repository.NoteRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val noteRepositoryImplModule = module {
    single<NoteRepository>(qualifier = named("noteRepositoryImpl")) {
        NoteRepositoryImpl(get())
    }

}
package com.magnum.noteapp.domain.di

import com.magnum.noteapp.domain.use_case.CreateNoteUseCase
import com.magnum.noteapp.domain.use_case.DeleteNoteUseCase
import com.magnum.noteapp.domain.use_case.GetNoteUseCase
import com.magnum.noteapp.domain.use_case.GetNotesUseCase
import com.magnum.noteapp.domain.use_case.UpdateNoteUseCase
import org.koin.dsl.module

val noteUseCaseModule = module {

    single { CreateNoteUseCase(get()) }
    single { UpdateNoteUseCase(get()) }
    single { GetNoteUseCase(get()) }
    single { GetNotesUseCase(get()) }
    single { DeleteNoteUseCase(get()) }
}
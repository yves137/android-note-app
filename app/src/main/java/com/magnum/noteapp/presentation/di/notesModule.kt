package com.magnum.noteapp.presentation.di

import com.magnum.noteapp.presentation.viewModel.NoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val notesModule = module {
    viewModel { NoteViewModel(get()) }
}
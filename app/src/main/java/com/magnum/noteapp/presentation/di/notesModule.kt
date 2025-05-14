package com.magnum.noteapp.presentation.di

import com.magnum.noteapp.presentation.viewModel.CreateNoteViewModel
import com.magnum.noteapp.presentation.viewModel.DeleteNoteViewModel
import com.magnum.noteapp.presentation.viewModel.GetNoteByIdViewModel
import com.magnum.noteapp.presentation.viewModel.GetNotesViewModel
import com.magnum.noteapp.presentation.viewModel.UpdateNoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val notesModule = module {
    viewModel { GetNotesViewModel(get()) }
    viewModel { GetNoteByIdViewModel(get()) }
    viewModel { CreateNoteViewModel(get()) }
    viewModel { UpdateNoteViewModel(get()) }
    viewModel { DeleteNoteViewModel(get()) }
}
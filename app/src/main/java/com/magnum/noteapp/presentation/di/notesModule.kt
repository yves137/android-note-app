package com.magnum.noteapp.presentation.di

import com.magnum.noteapp.presentation.screens.create_note.CreateNoteViewModel
import com.magnum.noteapp.presentation.screens.list_all_notes.DeleteNoteViewModel
import com.magnum.noteapp.presentation.screens.shared_viewModel.GetNoteByIdViewModel
import com.magnum.noteapp.presentation.screens.list_all_notes.GetNotesViewModel
import com.magnum.noteapp.presentation.screens.update_note.UpdateNoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val notesModule = module {
    viewModel { GetNotesViewModel(get()) }
    viewModel { GetNoteByIdViewModel(get()) }
    viewModel { CreateNoteViewModel(get()) }
    viewModel { UpdateNoteViewModel(get()) }
    viewModel { DeleteNoteViewModel(get()) }
}
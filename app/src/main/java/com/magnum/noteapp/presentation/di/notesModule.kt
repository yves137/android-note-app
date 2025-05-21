package com.magnum.noteapp.presentation.di

import com.magnum.noteapp.presentation.screens.create_note.CreateNoteViewModel
import com.magnum.noteapp.presentation.screens.view_note.ViewNoteViewModel
import com.magnum.noteapp.presentation.screens.list_all_notes.ListAllNotesViewModel
import com.magnum.noteapp.presentation.screens.update_note.UpdateNoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val notesModule = module {
    viewModel { ListAllNotesViewModel(get(), get()) }
    viewModel { (noteId: String) -> ViewNoteViewModel(get(), noteId) }
    viewModel { CreateNoteViewModel(get()) }
    viewModel { (noteId: String) -> UpdateNoteViewModel(get(), get(), noteId) }
}
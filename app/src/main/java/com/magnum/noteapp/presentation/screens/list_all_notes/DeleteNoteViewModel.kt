package com.magnum.noteapp.presentation.screens.list_all_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.domain.use_case.DeleteNoteUseCase
import kotlinx.coroutines.launch

class DeleteNoteViewModel(private val deleteNoteUseCase: DeleteNoteUseCase) : ViewModel() {

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
        }
    }
}
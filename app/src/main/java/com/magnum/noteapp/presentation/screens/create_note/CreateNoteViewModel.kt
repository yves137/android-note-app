package com.magnum.noteapp.presentation.screens.create_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.domain.use_case.CreateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateNoteViewModel(private val createNoteUseCase: CreateNoteUseCase) : ViewModel() {

    data class CreateNoteUIState(
        val note: Note = Note(title = "", content = ""),
        val isSavable: Boolean = false
    )

    private val _note = MutableStateFlow(CreateNoteUIState())
    val note = _note

    fun createNote() {
        viewModelScope.launch {
            createNoteUseCase(_note.value.note)
        }
    }

    fun modifyNoteTitle(title: String) {
        _note.update {
            it.copy(note = it.note.copy(title = title), isSavable = title.isNotEmpty())
        }
    }

    fun modifyNoteContent(content: String) {
        _note.update {
            it.copy(note = it.note.copy(content = content))
        }
    }
}
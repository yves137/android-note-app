package com.magnum.noteapp.presentation.screens.update_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.domain.use_case.GetNoteUseCase
import com.magnum.noteapp.domain.use_case.UpdateNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UpdateNoteViewModel(
    private val updateNoteUseCase: UpdateNoteUseCase,
    getNoteUseCase: GetNoteUseCase,
    noteId: String
) : ViewModel() {
    data class UpdateNoteUIState(val note: Note? = null, val isSavable: Boolean = false)

    private val _note = MutableStateFlow(UpdateNoteUIState())
    val note = _note

    init {
        viewModelScope.launch {
            getNoteUseCase(noteId)?.let { foundNote ->
                _note.update {
                    it.copy(note = foundNote, isSavable = false)
                }
            }
        }
    }

    fun updateNote() {
        viewModelScope.launch {
            _note.value.note?.let {
                updateNoteUseCase(it)
            }
        }
    }

    fun modifyNote(note: Note) {
        _note.update {
            it.copy(note = note)
        }
    }

    fun modifyNoteTitle(title: String) {
        _note.update {
            it.copy(note = it.note?.copy(title = title), isSavable = title.isNotEmpty())
        }
    }

    fun modifyNoteContent(content: String) {
        _note.update {
            it.copy(
                note = it.note?.copy(content = content),
                isSavable = it.note?.title?.isNotEmpty() == true
            )
        }
    }
}
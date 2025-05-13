package com.magnum.noteapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnum.noteapp.data.database.ApplicationDb
import com.magnum.noteapp.data.database.entity.NoteEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(notesDb: ApplicationDb) : ViewModel() {
    private val noteDao = notesDb.notesDatabase.getNotesDao()

    data class DetailNoteUIState(val note: NoteEntity?, val isSavable: Boolean = false)


    private val _detailNoteUIState = MutableStateFlow(DetailNoteUIState(note = null))
    val detailNoteUIState: StateFlow<DetailNoteUIState> = _detailNoteUIState

    val allNotes = noteDao.getAllNotes()

    fun insertNote(note: NoteEntity) {
        viewModelScope.launch {
            noteDao.insertNote(note)
        }
    }

    fun saveCurrentNote() {
        viewModelScope.launch {
            val note = detailNoteUIState.value.note
            if (note == null) throw RuntimeException("Note is null")
            else
                noteDao.updateNote(note)

        }
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch {
            noteDao.updateNote(note)
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            noteDao.deleteNote(note)
        }
    }

    fun getNoteById(id: String) {
        viewModelScope.launch {
            val note = noteDao.getNoteById(id)
            _detailNoteUIState.update {
                it.copy(note = note)
            }
        }
    }

    fun updateTitle(title: String) {
        _detailNoteUIState.update {
            it.copy(note = it.note?.copy(title = title),
                isSavable = title.isNotEmpty())
        }
    }

    fun updateContent(content: String) {
        _detailNoteUIState.update {
            it.copy(note = it.note?.copy(content = content))
        }
    }
}
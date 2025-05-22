package com.magnum.noteapp.presentation.screens.list_all_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.domain.use_case.DeleteNoteUseCase
import com.magnum.noteapp.domain.use_case.GetNoteUseCase
import com.magnum.noteapp.domain.use_case.GetNotesUseCase
import com.magnum.noteapp.domain.use_case.NoteTimerUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListAllNotesViewModel(
    getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    noteTimerUseCase: NoteTimerUseCase
) : ViewModel() {

    data class ListAllNotesUIState(
        val notes: List<Note> = emptyList()
    )

    val timer = noteTimerUseCase.invoke().stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val notes = getNotesUseCase.invoke().map {
        ListAllNotesUIState(it)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ListAllNotesUIState())

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            getNoteUseCase.invoke(noteId)?.let { note ->
                deleteNoteUseCase(note)
            }
        }
    }

}
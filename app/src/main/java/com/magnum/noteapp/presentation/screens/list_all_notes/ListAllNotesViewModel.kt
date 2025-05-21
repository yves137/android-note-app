package com.magnum.noteapp.presentation.screens.list_all_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.domain.use_case.DeleteNoteUseCase
import com.magnum.noteapp.domain.use_case.GetNotesUseCase
import com.magnum.noteapp.domain.use_case.NoteTimerUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListAllNotesViewModel(
    getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    noteTimerUseCase: NoteTimerUseCase
) : ViewModel() {

    data class GetNotesUIState(
        val notes: List<Note> = emptyList()
    )

    val timer = noteTimerUseCase.invoke().stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val notes = getNotesUseCase.invoke().map {
        GetNotesUIState(it)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, GetNotesUIState())

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
        }
    }

}
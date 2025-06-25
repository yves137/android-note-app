package com.magnum.noteapp.presentation.screens.list_all_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.domain.use_case.DeleteNoteUseCase
import com.magnum.noteapp.domain.use_case.GetNoteUseCase
import com.magnum.noteapp.domain.use_case.GetNotesUseCase
import com.magnum.noteapp.domain.use_case.NoteTimerUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListAllNotesViewModel(
    getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    noteTimerUseCase: NoteTimerUseCase
) : ViewModel() {

    data class ListAllNotesUIState(
        val notes: List<Note> = emptyList(),
        val timer: String = ""
    )

    private val notesFlow = getNotesUseCase.invoke()
    private val timerFlow = noteTimerUseCase.invoke()


    val combinedState = combine(notesFlow, timerFlow) { notes, timer ->
        ListAllNotesUIState(
            notes = notes,
            timer = timer
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ListAllNotesUIState())

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            getNoteUseCase.invoke(noteId)?.let { note ->
                deleteNoteUseCase(note)
            }
        }
    }

}
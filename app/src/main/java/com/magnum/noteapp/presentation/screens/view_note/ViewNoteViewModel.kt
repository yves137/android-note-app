package com.magnum.noteapp.presentation.screens.view_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.domain.use_case.GetNoteUseCase
import com.magnum.noteapp.domain.use_case.NoteTimerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewNoteViewModel(
    private val getNoteUseCase: GetNoteUseCase,
    noteTimerUseCase: NoteTimerUseCase,
    noteId: String
) :
    ViewModel() {

    private val _note = MutableStateFlow<Note?>(null)
    val note = _note
    val timer = noteTimerUseCase.invoke().stateIn(viewModelScope, SharingStarted.Eagerly, "")

    init {
        viewModelScope.launch {
            getNoteUseCase.invoke(noteId)?.let { foundNote ->
                _note.update { foundNote }
            }
        }
    }
}
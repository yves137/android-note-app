package com.magnum.noteapp.presentation.screens.shared_viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.domain.use_case.GetNoteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GetNoteByIdViewModel(private val getNoteUseCase: GetNoteUseCase) : ViewModel() {

    private val _note = MutableStateFlow<Note?>(null)
    val note = _note

    fun loadNoteById(id: String) {
        viewModelScope.launch {
            val note = getNoteUseCase(id)
            _note.value = note
        }
    }
}
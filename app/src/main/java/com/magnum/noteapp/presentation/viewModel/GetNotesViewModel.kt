package com.magnum.noteapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.domain.use_case.GetNotesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GetNotesViewModel(getNotesUseCase: GetNotesUseCase) : ViewModel() {

    data class GetNotesUIState(
        val notes: List<Note> = emptyList()
    )

    val notes = getNotesUseCase.invoke().map {
        GetNotesUIState(it)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, GetNotesUIState())

}
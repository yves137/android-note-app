package com.magnum.noteapp.domain.use_case

import com.magnum.noteapp.domain.repository.NoteRepository

class GetNotesUseCase(private val noteRepository: NoteRepository) {

    operator fun invoke() = noteRepository.getAllNotes()

}
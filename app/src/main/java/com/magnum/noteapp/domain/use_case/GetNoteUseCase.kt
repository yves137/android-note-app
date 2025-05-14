package com.magnum.noteapp.domain.use_case

import com.magnum.noteapp.domain.repository.NoteRepository

class GetNoteUseCase(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(noteId: String) = noteRepository.getNoteById(noteId)

}
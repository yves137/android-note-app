package com.magnum.noteapp.domain.use_case

import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.domain.repository.NoteRepository

class DeleteNoteUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(note: Note) {
        noteRepository.deleteNote(note)
    }
}
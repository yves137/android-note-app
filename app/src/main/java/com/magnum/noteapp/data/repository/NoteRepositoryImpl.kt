package com.magnum.noteapp.data.repository

import com.magnum.noteapp.data.database.ApplicationDb
import com.magnum.noteapp.data.database.entity.NoteEntity
import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(val applicationDb: ApplicationDb) : NoteRepository {
    private val notesDao = applicationDb.notesDatabase.getNotesDao()

    override suspend fun insertNote(note: Note) {
        notesDao.insertNote(note.toNoteEntity())
    }

    override suspend fun updateNote(note: Note) {
        notesDao.updateNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(note.toNoteEntity())
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes().map { it.map { it.toNote() } }
    }

    override suspend fun getNoteById(id: String): Note? {
        return notesDao.getNoteById(id)?.toNote()
    }
}

private fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content
    )
}

private fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content
    )
}

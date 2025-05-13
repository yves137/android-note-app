package com.magnum.noteapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.magnum.noteapp.data.database.dao.NotesDao
import com.magnum.noteapp.data.database.entity.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class NotesDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "notes_db"
    }

    abstract fun getNotesDao(): NotesDao
}
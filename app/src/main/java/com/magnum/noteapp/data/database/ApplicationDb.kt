package com.magnum.noteapp.data.database

import android.app.Application
import androidx.room.Room

class ApplicationDb(application: Application) {
    val notesDatabase = Room.databaseBuilder(
    application,
    NotesDatabase::class.java,
    NotesDatabase.DATABASE_NAME
    ).build()
}
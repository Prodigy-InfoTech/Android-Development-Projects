package com.phoenix.note.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phoenix.note.data.dao.NoteDao
import com.phoenix.note.data.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
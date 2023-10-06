package com.phoenix.note.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.phoenix.note.data.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note): Int

    @Delete
    suspend fun deleteNote(note: Note): Int

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<Note>

}
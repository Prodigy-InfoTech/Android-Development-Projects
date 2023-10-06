package com.phoenix.note.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.phoenix.note.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: Note): Long

    @Update
    suspend fun updateNote(note: Note): Long

    @Delete
    suspend fun deleteNote(note: Note): Long

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): Flow<List<Note>>

}
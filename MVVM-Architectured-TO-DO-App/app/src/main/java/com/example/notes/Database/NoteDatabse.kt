package com.example.notes.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notes.Dao.NotesDao
import com.example.notes.Entity.NotesEntity

@Database(entities = arrayOf(NotesEntity::class), version = 1, exportSchema = false)
public abstract class NoteDatabse : RoomDatabase() {

    abstract  fun noteDao() : NotesDao

    // similar to static in java
    companion object{

        @Volatile
        private var INSTANCE : NoteDatabse?= null

        fun getDatabase(context : Context) : NoteDatabse{

            return INSTANCE ?: synchronized(this ){
                val instance = Room.databaseBuilder(context.applicationContext,NoteDatabse::class.java,"Notes").allowMainThreadQueries().build();
                INSTANCE= instance
                return instance
            }
        }

    }

}
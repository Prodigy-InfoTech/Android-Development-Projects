package com.phoenix.note

import android.content.Context
import androidx.room.Room
import com.phoenix.note.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        ).build()

    @Provides
    @Singleton
    fun provideNoteDao(database: AppDatabase) =
        database.noteDao()

}
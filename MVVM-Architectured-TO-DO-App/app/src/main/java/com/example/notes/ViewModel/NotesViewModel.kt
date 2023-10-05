package com.example.notes.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notes.Dao.NotesDao
import com.example.notes.Database.NoteDatabse
import com.example.notes.Entity.NotesEntity
import com.example.notes.Repository.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val repository:NotesRepository

    init{
        val dao= NoteDatabse.getDatabase(application).noteDao()
        repository= NotesRepository(dao)
    }

    fun addNotes(notes:NotesEntity){
        repository.insertNotes(notes)
    }

    fun updateNotes(notes: NotesEntity){
        repository.updateNotes(notes)
    }
    fun getAllNotes():LiveData<List<NotesEntity>>{
        return repository.getAllNotes()
    }

    fun deleteNotes(id:Int){
        repository.deleteNotes(id)
    }

    fun getHighNotes() : LiveData<List<NotesEntity>>{
        return repository.getHighNotes()
    }



    fun getMediumNotes() : LiveData<List<NotesEntity>>{
        return repository.getMediumNotes()
    }


    fun getLowNotes() : LiveData<List<NotesEntity>>{
        return repository.getLowNotes()
    }

}
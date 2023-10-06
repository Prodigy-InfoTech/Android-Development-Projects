package com.phoenix.note.screen.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenix.note.data.dao.NoteDao
import com.phoenix.note.data.model.Note
import com.phoenix.note.screen.DialogState
import com.ramcosta.composedestinations.result.ResultBackNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    val noteDao: NoteDao,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState = _uiState.asStateFlow()
    private val _note = MutableStateFlow<Note?>(null)
    val note = _note.asStateFlow()

    private lateinit var navigator: ResultBackNavigator<Boolean>

    fun assignNavigator(navigator: ResultBackNavigator<Boolean>) {
        this.navigator = navigator
    }

    fun assignNote(note: Note?) {
        _note.update { note }
        _note.value?.let {
            _uiState.update {
                it.copy(
                    title = note!!.title,
                    note = note.message
                )
            }
        }
    }

    fun onEvent(event: NoteUiEvent) {
        when (event) {
            is NoteUiEvent.OnTitleChange ->
                _uiState.update { it.copy(title = event.title) }

            is NoteUiEvent.OnNoteChange ->
                _uiState.update { it.copy(note = event.note) }

            NoteUiEvent.SaveNote ->
                viewModelScope.launch {
                    saveNote()
                }.invokeOnCompletion {
                    navigator.navigateBack(result = true)
                }

            NoteUiEvent.DeleteNote ->
                viewModelScope.launch {
                    noteDao.deleteNote(note.value!!)
                }.invokeOnCompletion {
                    navigator.navigateBack(result = true)
                }

            is NoteUiEvent.OpenDialogue ->
                _uiState.update { it.copy(dialogState = event.dialogState) }

            NoteUiEvent.DismissDialogue ->
                _uiState.update { it.copy(dialogState = DialogState.None) }

        }
    }

    private suspend fun saveNote() {
        if (note.value == null)
            noteDao.insertNote(
                Note(
                    title = uiState.value.title,
                    message = uiState.value.note,
                    dateCreated = if (note.value != null) note.value!!.dateCreated else System.currentTimeMillis(),
                    dateUpdated = System.currentTimeMillis()
                )
            )
        else
            noteDao.updateNote(
                note.value!!.copy(
                    title = uiState.value.title,
                    message = uiState.value.note,
                    dateUpdated = System.currentTimeMillis()
                )
            )
    }

}
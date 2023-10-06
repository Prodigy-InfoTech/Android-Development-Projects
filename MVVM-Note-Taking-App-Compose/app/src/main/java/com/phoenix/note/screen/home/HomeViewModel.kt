package com.phoenix.note.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenix.note.data.dao.NoteDao
import com.phoenix.note.data.model.Note
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val noteDao: NoteDao
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    private lateinit var navigator: DestinationsNavigator

    init {
        viewModelScope.launch {
            noteDao.getAllNotes().collect {
                _uiState.update { it.copy(displayLoading = true) }
                _notes.update { it }
                _uiState.update { it.copy(displayLoading = false) }
            }
        }
    }

    fun assignNavigator(navigator: DestinationsNavigator) {
        this.navigator = navigator
    }

    fun onEvent(event: HomeUiEvent) {
        when(event) {
            HomeUiEvent.OnClickFAB -> {}

            is HomeUiEvent.OnClickNote -> {}

            HomeUiEvent.DismissDialogue ->
                _uiState.update { it.copy(dialogState = DialogState.None) }

            is HomeUiEvent.OpenDeleteDialog ->
                _uiState.update { it.copy(dialogState = DialogState.Delete(event.noteId)) }

            is HomeUiEvent.DeleteNote ->
                viewModelScope.launch {
                    noteDao.deleteNote(event.note)
                }

        }
    }
}
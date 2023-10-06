package com.phoenix.note.screen.home

import com.phoenix.note.data.model.Note

sealed interface HomeUiEvent {
    data class OnClickNote(val noteId: Int): HomeUiEvent
    data object OnClickFAB: HomeUiEvent
    data class OpenDeleteDialog(val noteId: Int): HomeUiEvent
    data object DismissDialogue: HomeUiEvent
    data class DeleteNote(val note: Note): HomeUiEvent
}
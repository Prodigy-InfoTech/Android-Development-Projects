package com.phoenix.note.screen.note

import com.phoenix.note.screen.DialogState

sealed interface NoteUiEvent {
    data class OnTitleChange(val title: String): NoteUiEvent
    data class OnNoteChange(val note: String): NoteUiEvent
    data object SaveNote: NoteUiEvent
    data object DismissDialogue: NoteUiEvent
    data class OpenDialogue(val dialogState: DialogState): NoteUiEvent
    data object DeleteNote: NoteUiEvent
}
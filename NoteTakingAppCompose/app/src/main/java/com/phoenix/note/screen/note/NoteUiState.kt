package com.phoenix.note.screen.note

import com.phoenix.note.screen.DialogState

data class NoteUiState(
    val title: String = "",
    val note: String = "",
    val dialogState: DialogState = DialogState.None
)
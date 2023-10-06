package com.phoenix.note.screen.home

data class HomeUiState(
    val displayLoading: Boolean = false,
    val dialogState: DialogState = DialogState.None
)

sealed interface DialogState {
    data object None: DialogState
    data class Delete(val noteId: Int): DialogState
}
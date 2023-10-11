package com.phoenix.note.screen.home

import com.phoenix.note.screen.DialogState

data class HomeUiState(
    val displayLoading: Boolean = false,
    val dialogState: DialogState = DialogState.None
)

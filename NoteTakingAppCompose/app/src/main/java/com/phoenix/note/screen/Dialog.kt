package com.phoenix.note.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.phoenix.note.R

/**
 * I changed confirm button and dismiss button so negative CTA (like delete or discard)
 * wont trigger by mistake
 */
object Dialog {

    @Composable
    fun DeleteDialog(
        onDismiss: () -> Unit,
        onDelete: () -> Unit,
    ) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = stringResource(R.string.delete_note)) },
            text = { Text(text = stringResource(R.string.delete_message)) },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(stringResource(R.string.cancel))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    onDismiss()
                }) {
                    Text(
                        stringResource(R.string.delete),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
    }

    @Composable
    fun UnsavedChanges(
        onDismiss: () -> Unit,
        onConfirm: () -> Unit,
    ) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = stringResource(R.string.discard_changes))},
            text = { Text(text = stringResource(R.string.exit_message)) },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text(stringResource(R.string.keep))
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm()
                    onDismiss()
                }) {
                    Text(
                        stringResource(R.string.discard),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
    }
}

sealed interface DialogState {
    data object None : DialogState
    data class Delete(val noteId: Int) : DialogState
    data object UnsavedChanges : DialogState
}
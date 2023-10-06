package com.phoenix.note.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.phoenix.note.R

object Dialog {

    @Composable
    fun DeleteDialog(
        onDismiss: () -> Unit,
        onDelete: () -> Unit,
        title: String,
    ) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Text(text = title)
            },
            text = { Text(text = stringResource(R.string.delete_message)) },
            confirmButton = {
                Button(
                    onClick = { onDismiss() },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.cancel))
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        onDelete()
                        onDismiss()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(
                        ButtonDefaults.outlinedButtonBorder.width,
                        MaterialTheme.colorScheme.error
                    )
                ) {
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
        onConfirm: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            text = { Text(text = stringResource(R.string.exit_message)) },
            confirmButton = {
                Button(
                    onClick = { onDismiss() },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.keep))
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(
                        ButtonDefaults.outlinedButtonBorder.width,
                        MaterialTheme.colorScheme.error
                    )
                ) {
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
    data object None: DialogState
    data class Delete(val noteId: Int): DialogState
    data object UnsavedChanges: DialogState
}
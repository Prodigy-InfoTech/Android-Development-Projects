package com.phoenix.note.screen.note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phoenix.note.R
import com.phoenix.note.data.model.Note
import com.phoenix.note.screen.Dialog
import com.phoenix.note.screen.DialogState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.launch

/**
 * @Destination annotation is used to define nav graph routes with ComposeDestinations
 */
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun NoteScreen(
    navigator: ResultBackNavigator<Boolean>,
    noteViewModel: NoteViewModel = hiltViewModel(),
    editableNote: Note?,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by noteViewModel.uiState.collectAsStateWithLifecycle()
    val note by noteViewModel.note.collectAsStateWithLifecycle()

    /**
     * Pass Note model to ViewModel to check if its empty or not
     * If not set title and message based on given Note
     */
    LaunchedEffect(Unit) {
        noteViewModel.assignNavigator(navigator)
        noteViewModel.assignNote(editableNote)
    }

    /**
     * Display unsaved change dialog when user accidentally press back button
     */
    BackHandler(uiState.title.isNotEmpty() || uiState.note.isNotEmpty()) {
        noteViewModel.onEvent(NoteUiEvent.OpenDialogue(DialogState.UnsavedChanges))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.back),
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { noteViewModel.onEvent(NoteUiEvent.OpenDialogue(DialogState.UnsavedChanges)) }
                            .padding(4.dp)
                    )
                },
                actions = {
                    note?.let {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.delete),
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    /**
                                     * Request ViewModel to change dialog state
                                     * And open delete dialog
                                     */
                                    noteViewModel.onEvent(
                                        NoteUiEvent.OpenDialogue(
                                            DialogState.Delete(note!!.id)
                                        )
                                    )
                                }
                                .padding(4.dp)
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (uiState.title.isEmpty() && uiState.note.isEmpty())
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(context.getString(R.string.save_empty_fields))
                        }
                    else
                        noteViewModel.onEvent(NoteUiEvent.SaveNote)
                }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(R.string.save)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = stringResource(R.string.save))
                }
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            TextField(
                value = uiState.title,
                onValueChange = { noteViewModel.onEvent(NoteUiEvent.OnTitleChange(it)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                textStyle = TextStyle(
                    fontSize = 20.sp
                ),
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.title),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                /**
                 * Remove text field background and Indicator so it would be more beautiful
                 */
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )
            TextField(
                value = uiState.note,
                onValueChange = { noteViewModel.onEvent(NoteUiEvent.OnNoteChange(it)) },
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                placeholder = {
                    Text(
                        text = stringResource(R.string.note),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                /**
                 * Remove text field background and Indicator so it would be more beautiful
                 */
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )
        }
    }

    /**
     * Check dialog state and select a @Composable suitable for dialog state
     */
    if (uiState.dialogState != DialogState.None) {
        when (uiState.dialogState) {
            DialogState.None -> {}
            is DialogState.Delete -> {
                Dialog.DeleteDialog(
                    onDismiss = { noteViewModel.onEvent(NoteUiEvent.DismissDialogue) },
                    onDelete = { noteViewModel.onEvent(NoteUiEvent.DeleteNote) },
                    title = note!!.title
                )
            }

            DialogState.UnsavedChanges -> {
                Dialog.UnsavedChanges(
                    onDismiss = { noteViewModel.onEvent(NoteUiEvent.DismissDialogue) },
                    onConfirm = { navigator.navigateBack(result = false) }
                )
            }
        }

    }
}
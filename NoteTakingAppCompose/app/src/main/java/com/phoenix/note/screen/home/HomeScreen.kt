package com.phoenix.note.screen.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phoenix.note.R
import com.phoenix.note.screen.Dialog
import com.phoenix.note.screen.DialogState
import com.phoenix.note.screen.destinations.NoteScreenDestination
import com.phoenix.note.screen.home.widget.HomeNote
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

/**
 * This composable annotated with @RootNavGraph(start = true) so it will be start route of
 * root nav graph
 *
 * @Destination annotation is used to define nav graph routes with ComposeDestinations
 */
@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel(),
    noteResultRecipient: ResultRecipient<NoteScreenDestination, Boolean>,
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val notes by homeViewModel.notes.collectAsStateWithLifecycle()
    /**
     * Receive a boolean from NoteScreen to check if any changes made in database
     * If true then refresh notes taken from database
     */
    noteResultRecipient.onNavResult {
        when (it) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (it.value) homeViewModel.onEvent(HomeUiEvent.Refresh)
            }
        }
    }

    LaunchedEffect(Unit) {
        homeViewModel.assignNavigator(navigator)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(stringResource(R.string.app_name)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { homeViewModel.onEvent(HomeUiEvent.OnClickFAB) }) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = stringResource(R.string.new_note)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = stringResource(R.string.new_note))
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        /**
         * Used a cross fade to animate between loading and actual list
         */
        Crossfade(targetState = uiState.displayLoading, label = "") { isLoading ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center,
            ) {
                if (isLoading)
                    CircularProgressIndicator()
                else {
                    if (notes.isEmpty())
                        Text(text = stringResource(R.string.note_empty_message))

                    LazyVerticalStaggeredGrid(
                        StaggeredGridCells.Adaptive(150.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(notes.reversed()) { note ->
                            HomeNote(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 8.dp),
                                note,
                                onClick = {
                                    homeViewModel.onEvent(HomeUiEvent.OnClickNote(note.id))
                                },
                                onClickDelete = {
                                    homeViewModel.onEvent(HomeUiEvent.OpenDeleteDialog(it))
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Check if delete dialog is requested for any note
     */
    if (uiState.dialogState is DialogState.Delete) {
        val note = notes.first { it.id == (uiState.dialogState as DialogState.Delete).noteId }
        Dialog.DeleteDialog(
            onDismiss = { homeViewModel.onEvent(HomeUiEvent.DismissDialogue) },
            onDelete = {
                homeViewModel.onEvent(HomeUiEvent.DeleteNote(note))
                homeViewModel.onEvent(HomeUiEvent.Refresh)
            },
        )
    }
}

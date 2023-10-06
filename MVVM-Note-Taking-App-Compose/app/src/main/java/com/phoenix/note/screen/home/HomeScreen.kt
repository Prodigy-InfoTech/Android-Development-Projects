package com.phoenix.note.screen.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.phoenix.note.R
import com.phoenix.note.screen.home.widget.HomeNote
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val notes by homeViewModel.notes.collectAsStateWithLifecycle()

    homeViewModel.assignNavigator(navigator)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(stringResource(R.string.app_name)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { homeViewModel.onEvent(HomeUiEvent.OnClickFAB) }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.new_note)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = stringResource(R.string.new_note))
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Crossfade(targetState = uiState.displayLoading, label = "") { isLoading ->
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    LazyVerticalStaggeredGrid(
                        StaggeredGridCells.Adaptive(120.dp),
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (notes.isEmpty())
                            item {
                                Box(Modifier.fillMaxSize()) {
                                    Text(text = stringResource(R.string.note_empty_message))
                                }
                            }
                        items(notes) { note ->
                            HomeNote(
                                modifier = Modifier.fillMaxWidth(),
                                note,
                                onClick = {
                                    homeViewModel.onEvent(HomeUiEvent.OnClickNote(note.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if(uiState.dialogState != DialogState.None) {
        val note = notes.first { it.id == (uiState.dialogState as DialogState.Delete).noteId }
        AlertDialog(
            onDismissRequest = { homeViewModel.onEvent(HomeUiEvent.DismissDialogue) },
            title = {
                Text(text = note.title)
                    },
            text = { Text(text = stringResource(R.string.delete_message)) },
            confirmButton = {
                Button(
                    onClick = { homeViewModel.onEvent(HomeUiEvent.DismissDialogue) },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.cancel))
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { homeViewModel.onEvent(HomeUiEvent.DeleteNote(note)) },
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
}

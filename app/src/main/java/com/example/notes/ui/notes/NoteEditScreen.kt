package com.example.notes.ui.notes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.AppViewModelProvider
import com.example.notes.NotesTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    navigateBack: () -> Unit,
    viewModel: NoteEditViewModel = viewModel(factory = AppViewModelProvider.factory)
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            NotesTopAppBar(
                title = "",
                canNavigateBack = true,
                navigateUp = navigateBack,
                actions = {
                    IconButton(
                        onClick = { viewModel.updateNote(navigateBack) },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        NoteEntryContent(
            itemUiState = uiState,
            onNotesValueChange = viewModel::updateUiState,
            modifier = Modifier.padding(innerPadding).fillMaxWidth()
        )
    }
}
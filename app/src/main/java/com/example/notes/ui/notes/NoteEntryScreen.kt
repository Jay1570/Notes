package com.example.notes.ui.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.AppViewModelProvider
import com.example.notes.NotesTopAppBar
import com.example.notes.ui.theme.NotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEntryScreen(
    navigateBack: () -> Unit,
    viewModel: NoteEntryViewModel = viewModel(factory = AppViewModelProvider.factory)
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
                        onClick = { viewModel.saveItem(navigateBack) },
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

@Composable
fun NoteEntryContent(
    itemUiState: NoteUiState,
    onNotesValueChange: (NoteDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        TextField(
            value = itemUiState.noteDetails.title,
            onValueChange = {
                onNotesValueChange(itemUiState.noteDetails.copy(title = it))
            },
            placeholder = { Text("Title", style = MaterialTheme.typography.headlineSmall) },
            textStyle = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
            ),
            singleLine = true,

        )
        TextField(
            value = itemUiState.noteDetails.description,
            onValueChange = {
                onNotesValueChange(itemUiState.noteDetails.copy(description = it))
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            placeholder = { Text("Description", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier.fillMaxWidth().weight(1f),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoteEntryPreview() {
    NotesTheme {
        NoteEntryContent(
            itemUiState = NoteUiState(),
            onNotesValueChange = {},
        )
    }
}
package com.example.notes.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.AppViewModelProvider
import com.example.notes.NotesTopAppBar
import com.example.notes.R
import com.example.notes.data.Note
import com.example.notes.ui.theme.NotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToNoteEdit: (Int) -> Unit,
    navigateToNoteEntry: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NotesTopAppBar(
                title = "Home",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToNoteEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.item_entry_title)
                )
            }
        }
    ) { innerPadding ->
        HomeContent(
            notesList = uiState.noteList,
            onItemClick = navigateToNoteEdit,
            onDeleteClick = viewModel::deleteNote,
            isLoading = uiState.isLoading,
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
    }
}

@Composable
private fun HomeContent(
    notesList: List<Note>,
    onItemClick: (Int) -> Unit,
    onDeleteClick: (Note) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        when {

            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(contentPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            notesList.isEmpty() -> {
                Text(
                    text = stringResource(id = R.string.no_item_description),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(contentPadding)
                )
            }

            else -> {
                NotesList(
                    notesList = notesList,
                    onItemClick = onItemClick,
                    onDeleteClick = onDeleteClick,
                    contentPadding = contentPadding,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
                )
            }
        }
    }
}

@Composable
private fun NotesList(
    notesList: List<Note>,
    onItemClick: (Int) -> Unit,
    onDeleteClick: (Note) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = notesList, key = { it.id }) { item ->
            InventoryItem(
                item = item,
                onDeleteClick = onDeleteClick,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .clickable(onClick = { onItemClick(item.id) })
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun InventoryItem(
    item: Note,
    onDeleteClick: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large))
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = { onDeleteClick(item) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Note"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    NotesTheme {
        HomeContent(
            listOf(
                Note(
                    1,
                    "Game",
                    "grfergergruygecckhwwcvdgkwedewfdjtewdwetydvwekcvcvecvccjh,ecve,nvcvd,cndvceveh"
                ), Note(2, "Pen", ""), Note(3, "TV", "")
            ),
            onItemClick = {},
            onDeleteClick = {},
            isLoading = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentEmptyListPreview() {
    NotesTheme {
        HomeContent(listOf(), onItemClick = {}, onDeleteClick = {}, isLoading = false)
    }
}

@Preview(showBackground = true)
@Composable
fun InventoryItemPreview() {
    NotesTheme {
        InventoryItem(
            Note(
                1,
                "Game",
                "grfergergruygecckhwwcvdgkwedewfdjtewdwetydvwekcvcvecvccjh,ecve,nvcvd,cndvceveh"
            ),
            onDeleteClick = {}
        )
    }
}
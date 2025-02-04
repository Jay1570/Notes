package com.example.notes.ui.notes

import com.example.notes.data.Note

data class NoteUiState(
    val noteDetails: NoteDetails = NoteDetails(),
    val isEntryValid: Boolean = true
)

data class NoteDetails(
    val id: Int = 0,
    val title: String = "",
    val description: String = ""
)

fun NoteDetails.toNote(): Note = Note(
    id = id,
    title = title,
    description = description
)

fun Note.toNoteUiState(isEntryValid: Boolean = false): NoteUiState = NoteUiState(
    noteDetails = this.toNoteDetails(),
    isEntryValid = isEntryValid
)

fun Note.toNoteDetails(): NoteDetails = NoteDetails(
    id = id,
    title = title,
    description = description
)

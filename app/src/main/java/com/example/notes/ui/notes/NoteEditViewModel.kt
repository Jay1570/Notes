package com.example.notes.ui.notes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.notes.NoteEdit
import com.example.notes.data.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NoteEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState = _uiState.asStateFlow()

    private val noteId = savedStateHandle.toRoute<NoteEdit>().id

    init {
        viewModelScope.launch {
            _uiState.value =
                noteRepository
                    .getNoteStream(noteId)
                    .filterNotNull()
                    .first()
                    .toNoteUiState(true)
        }
    }

    fun updateNote(navigateBack: () -> Unit) {
        viewModelScope.launch {
            if (validateInput()) {
                noteRepository.updateNote(_uiState.value.noteDetails.toNote())
                navigateBack()
            }
        }
    }

    fun updateUiState(noteDetails: NoteDetails) {
        _uiState.value = NoteUiState(
            noteDetails = noteDetails, isEntryValid = validateInput(noteDetails)
        )
    }

    private fun validateInput(noteDetails: NoteDetails = _uiState.value.noteDetails): Boolean {
        return with(noteDetails) {
            title.isNotBlank() && description.isNotBlank()
        }
    }
}
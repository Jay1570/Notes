package com.example.notes.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.data.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteEntryViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState = _uiState.asStateFlow()

    fun updateUiState(noteDetails: NoteDetails) {
        _uiState.value = NoteUiState(
            noteDetails = noteDetails, isEntryValid = validateInput(noteDetails)
        )
    }

    fun saveItem(navigateBack: () -> Unit) {
        viewModelScope.launch {
            if (validateInput()) {
                noteRepository.insertNote(_uiState.value.noteDetails.toNote())
                navigateBack()
            }
        }
    }

    private fun validateInput(noteDetails: NoteDetails = _uiState.value.noteDetails): Boolean {
        return with(noteDetails) {
            title.isNotBlank() && description.isNotBlank()
        }
    }
}

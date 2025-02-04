package com.example.notes

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notes.ui.notes.NoteEditViewModel
import com.example.notes.ui.notes.NoteEntryViewModel
import com.example.notes.ui.home.HomeViewModel

object AppViewModelProvider {
    val factory = viewModelFactory {
        initializer {
            HomeViewModel(myApp().container.noteRepository)
        }

        initializer {
            NoteEntryViewModel(myApp().container.noteRepository)
        }

        initializer {
            NoteEditViewModel(
                this.createSavedStateHandle(),
                myApp().container.noteRepository
            )
        }
    }
}

fun CreationExtras.myApp(): MyApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MyApp)
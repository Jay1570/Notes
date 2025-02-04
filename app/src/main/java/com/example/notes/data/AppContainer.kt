package com.example.notes.data

import android.content.Context

class AppContainer(private val context: Context) {
    val noteRepository: NoteRepository by lazy {
        NoteRepositoryImpl(NotesDatabase.getDatabase(context).noteDao())
    }
}
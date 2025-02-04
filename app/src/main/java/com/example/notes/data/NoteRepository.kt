package com.example.notes.data

import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotesStream(): Flow<List<Note>>

    fun getNoteStream(id: Int): Flow<Note?>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun updateNote(note: Note)
}

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override fun getAllNotesStream(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override fun getNoteStream(id: Int): Flow<Note?> {
        return noteDao.getNote(id)
    }

    override suspend fun insertNote(note: Note) {
        return noteDao.insert(note)
    }

    override suspend fun deleteNote(note: Note) {
        return noteDao.delete(note)
    }

    override suspend fun updateNote(note: Note) {
        return noteDao.update(note)
    }
}
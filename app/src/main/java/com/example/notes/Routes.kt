package com.example.notes

import kotlinx.serialization.Serializable

sealed interface Routes

@Serializable
data object HomeScreen : Routes

@Serializable
data object NoteEntry : Routes

@Serializable
data class NoteEdit(
    val id: Int
) : Routes
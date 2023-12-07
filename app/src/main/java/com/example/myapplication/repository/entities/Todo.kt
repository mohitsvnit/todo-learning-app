package com.example.myapplication.repository.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Todo(
    val title: String,
    val description: String,
    val isDone: Boolean = false,
    @PrimaryKey val todoId: String = UUID.randomUUID().toString()
)

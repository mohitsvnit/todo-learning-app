package com.example.myapplication.repository.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.repository.entities.Todo

@Dao
interface TodoDao {

    @Insert
    suspend fun insert(todoList: List<Todo>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(todo: Todo)

    @Query("SELECT * FROM Todo")
    suspend fun getTodoList(): List<Todo>
}
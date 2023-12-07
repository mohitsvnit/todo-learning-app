package com.example.myapplication.repository

import android.content.Context
import androidx.room.Room
import com.example.myapplication.repository.daos.TodoDao
import com.example.myapplication.repository.entities.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoRepository(private val todoDao: TodoDao) {

    suspend fun addTodo(todo: Todo) {
        withContext(Dispatchers.IO) {
            todoDao.insert(listOf(todo))
        }
    }

    suspend fun getTodoList(): List<Todo> {
        return withContext(Dispatchers.IO) {
            todoDao.getTodoList()
        }
    }

    suspend fun toggleDone(todoId: String) {
        withContext(Dispatchers.IO) {
            val todoList = todoDao.getTodoList()

            val index = todoList.indexOfFirst { it.todoId == todoId }

            if (index in todoList.indices) {
                todoDao.update(todoList[index].copy(isDone = todoList[index].isDone.not()))
            }
        }
    }
}

object TodoRepositoryFactory {
    private var todoRepository: TodoRepository? = null

    fun getTodoRepository(context: Context): TodoRepository {
        return todoRepository ?: createTodoRepository(context).also {
            todoRepository = it
        }
    }

    private fun createTodoRepository(context: Context): TodoRepository {
        val db = Room.databaseBuilder(context, TodoDatabase::class.java, "todo_database")
            .build()

        return TodoRepository(db.getTodoDao())
    }
}
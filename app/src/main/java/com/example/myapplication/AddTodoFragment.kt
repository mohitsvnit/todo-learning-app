package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.interfaces.NavigationManager
import com.example.myapplication.interfaces.ScreenName
import com.example.myapplication.repository.entities.Todo
import com.example.myapplication.repository.TodoRepository
import com.example.myapplication.repository.TodoRepositoryFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.lang.Exception


class AddTodoFragment : Fragment() {

    private val todoRepository: TodoRepository by lazy {
        context?.let { TodoRepositoryFactory.getTodoRepository(it) } ?: throw Exception("Context cannot be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btn_add_todo).setOnClickListener {
            val title = view.findViewById<TextInputEditText>(R.id.et_title).text.toString()
            val desc = view.findViewById<TextInputEditText>(R.id.et_description).text.toString()

            if(title.isNotBlank() && desc.isNotBlank()) {
                lifecycleScope.launch {
                    todoRepository.addTodo(Todo(title, desc))
                    (activity as? NavigationManager)?.navigateTo(ScreenName.HOME_FRAGMENT)
                }
            }else {
                Toast.makeText(context, "Title or Desc cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
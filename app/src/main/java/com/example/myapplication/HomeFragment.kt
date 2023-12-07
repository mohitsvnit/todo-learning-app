package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.TodoAdapter
import com.example.myapplication.interfaces.NavigationManager
import com.example.myapplication.interfaces.ScreenName
import com.example.myapplication.repository.TodoRepository
import com.example.myapplication.repository.TodoRepositoryFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeFragment : Fragment(), TodoAdapter.Listener {

    private val todoAdapter by lazy { TodoAdapter(this) }
    private var navigationManager: NavigationManager? = null

    private val todoRepository: TodoRepository by lazy {
        context?.let { TodoRepositoryFactory.getTodoRepository(it) } ?: throw Exception("Context cannot be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        navigationManager = activity as? NavigationManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        navigationManager = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupRecyclerView()
        setData()
    }

    private fun setupClickListeners() {
        view?.findViewById<FloatingActionButton>(R.id.fab_add_todo)?.setOnClickListener {
            navigationManager?.navigateTo(ScreenName.ADD_TODO_FRAGMENT)
        }
    }

    private fun setData() {
        lifecycleScope.launch {
            todoAdapter.updateTodoList(todoRepository.getTodoList())
        }
    }

    private fun setupRecyclerView() {
        view?.findViewById<RecyclerView>(R.id.rv_todo_list)?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = todoAdapter
        }
    }

    override fun onMarkDoneToggle(todoItemPosition: Int) {
        lifecycleScope.launch {
            todoRepository.toggleDone(todoRepository.getTodoList()[todoItemPosition].todoId)

            todoAdapter.updateTodoList(todoRepository.getTodoList())
        }
    }

    override fun onTodoItemClick(todoItemPosition: Int) {
        lifecycleScope.launch {
            navigationManager?.navigateTo(
                ScreenName.TODO_FRAGMENT,
                todoRepository.getTodoList()[todoItemPosition]
            )
        }
    }
}
package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.repository.entities.Todo

class TodoAdapter(private val listener: Listener): RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private val todoList: MutableList<Todo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.todo_list_item, parent, false),
            listener
        )
    }

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    fun updateTodoList(todoList: List<Todo>) {
        this.todoList.clear()
        this.todoList.addAll(todoList)
        notifyDataSetChanged()
    }

    class TodoViewHolder(itemView: View, private val listener: Listener) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle: TextView by lazy { itemView.findViewById(R.id.tv_todo_title) }
        private val cbMarkDone: CheckBox by lazy { itemView.findViewById(R.id.cb_mark_done) }

        private var isMarkDone: Boolean = false

        fun bind(todo: Todo) {
            tvTitle.text = todo.title
            isMarkDone = false

            if(todo.isDone) {
                cbMarkDone.isChecked = true
                cbMarkDone.text = "Mark Undone"
                itemView.setBackgroundColor(itemView.context.resources.getColor(R.color.todo_item_marked))
                isMarkDone = true
            } else {
                cbMarkDone.isChecked = false
                cbMarkDone.text = "Mark Done"
                itemView.setBackgroundColor(itemView.context.resources.getColor(R.color.white))
            }

            cbMarkDone.setOnClickListener {
                listener.onMarkDoneToggle(adapterPosition)
            }

            tvTitle.setOnClickListener {
                listener.onTodoItemClick(adapterPosition)
            }
        }
    }

    interface Listener {
        fun onMarkDoneToggle(todoItemPosition: Int)
        fun onTodoItemClick(todoItemPosition: Int)
    }
}


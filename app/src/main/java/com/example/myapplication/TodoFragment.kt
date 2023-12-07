package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.interfaces.BackPressHandler
import com.example.myapplication.interfaces.NavigationManager
import com.example.myapplication.interfaces.ScreenName
import com.example.myapplication.repository.entities.Todo

class TodoFragment : Fragment(), BackPressHandler {
    companion object {

        private const val KEY_TITLE = "title"
        private const val KEY_DESCRIPTION = "desc"

        fun getInstance(todo: Todo): TodoFragment {
            return TodoFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_TITLE, todo.title)
                    putString(KEY_DESCRIPTION, todo.description)
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        view?.findViewById<TextView>(R.id.tv_title)?.text = arguments?.getString(KEY_TITLE)
        view?.findViewById<TextView>(R.id.tv_description)?.text = arguments?.getString(KEY_DESCRIPTION)
    }

    override fun onBackPress() {
        (activity as? NavigationManager)?.navigateTo(ScreenName.HOME_FRAGMENT)
    }
}
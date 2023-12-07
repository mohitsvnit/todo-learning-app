package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.interfaces.BackPressHandler
import com.example.myapplication.interfaces.NavigationManager
import com.example.myapplication.interfaces.ScreenName
import com.example.myapplication.repository.entities.Todo

class MainActivity : AppCompatActivity(), NavigationManager {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onStart() {
        super.onStart()
        navigateToHomeFragment()
    }

    private fun navigateToAddTodoFragment() {
        navigateTo(AddTodoFragment())
    }

    private fun navigateToHomeFragment() {
        navigateTo(HomeFragment())
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment)
            commit()
        }
    }

    override fun navigateTo(screenName: ScreenName, todo: Todo?) {
        when(screenName) {
            ScreenName.HOME_FRAGMENT -> navigateToHomeFragment()
            ScreenName.TODO_FRAGMENT -> todo?.let { navigateToTodoFragment(it) }
            ScreenName.ADD_TODO_FRAGMENT -> navigateToAddTodoFragment()
        }
    }

    private fun navigateToTodoFragment(todo: Todo) {
        navigateTo(TodoFragment.getInstance(todo))
    }

    override fun onBackPressed() {
        (supportFragmentManager.fragments.firstOrNull() as? BackPressHandler)?.onBackPress()

        super.onBackPressed()
    }
}




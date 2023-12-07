package com.example.myapplication.interfaces

import com.example.myapplication.repository.entities.Todo

interface NavigationManager {
    fun navigateTo(screenName: ScreenName, todo: Todo? = null)
}

enum class ScreenName {
    HOME_FRAGMENT,
    TODO_FRAGMENT,
    ADD_TODO_FRAGMENT;
}
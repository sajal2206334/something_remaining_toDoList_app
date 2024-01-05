package com.example.somethingremaining.data

import android.content.Context

interface AppContainer {
    val toDoRepository: ToDoRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val toDoRepository: ToDoRepository by lazy {
        ToDoRepository(SomethingRemainingDatabase.getDatabase(context).toDoDao())
    }
}
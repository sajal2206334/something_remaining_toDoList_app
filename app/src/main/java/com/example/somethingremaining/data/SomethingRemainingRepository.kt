package com.example.somethingremaining.data

import kotlinx.coroutines.flow.Flow

interface SomethingRemainingRepository {

    fun getAllToDo() : Flow<List<ToDo>>

    fun getItem(id : Int) : Flow<ToDo?>

    suspend fun insertToDo(toDo: ToDo)

    suspend fun updateToDo(toDo: ToDo)

    suspend fun deleteToDo(toDo: ToDo)

    //suspend fun updateIsCompleted(id: Int, isCompleted : Boolean)

    //suspend fun updateState(toDo: ToDo)
}
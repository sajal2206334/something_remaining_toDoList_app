package com.example.somethingremaining.data

import kotlinx.coroutines.flow.Flow

class ToDoRepository(private val toDoDao: ToDoDao) : SomethingRemainingRepository {

    override fun getAllToDo() : Flow<List<ToDo>> = toDoDao.getAllToDo()

    override fun getItem(id : Int) : Flow<ToDo?> = toDoDao.getItem(id)

    override suspend fun insertToDo(toDo: ToDo) = toDoDao.insert(toDo)

    override suspend fun updateToDo(toDo: ToDo) = toDoDao.update(toDo)

    override suspend fun deleteToDo(toDo: ToDo) = toDoDao.delete(toDo)

    //override suspend fun updateState(toDo: ToDo) = toDoDao.updateState(toDo)

    //override suspend fun updateIsCompleted(id: Int, isCompleted : Boolean) = toDoDao.updateIsCompleted(id, isCompleted)

}
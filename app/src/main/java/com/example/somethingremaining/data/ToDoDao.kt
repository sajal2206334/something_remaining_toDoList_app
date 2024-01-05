package com.example.somethingremaining.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(toDo: ToDo)

    @Update
    suspend fun update(toDo: ToDo)

    @Delete
    suspend fun delete(toDo: ToDo)

    @Query("SELECT * from something_remaining WHERE id = :id")
    fun getItem(id : Int) : Flow<ToDo>

    @Query("SELECT * from something_remaining ORDER BY date ASC")
    fun getAllToDo() : Flow<List<ToDo>>

    //@Update
    //suspend fun updateState(toDo: ToDo)

    //@Query("UPDATE something_remaining SET isCompleted = :isCompleted WHERE id = :id")
    //suspend fun updateIsCompleted(id: Int, isCompleted: Boolean)
}
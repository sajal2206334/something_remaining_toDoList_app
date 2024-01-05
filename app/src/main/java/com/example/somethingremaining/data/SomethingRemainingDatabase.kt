package com.example.somethingremaining.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDo::class], version = 1)
abstract class SomethingRemainingDatabase : RoomDatabase() {

    abstract fun toDoDao() : ToDoDao

    companion object {

        @Volatile
        private var INSTANCE : SomethingRemainingDatabase? = null

        fun getDatabase(context: Context) :SomethingRemainingDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, SomethingRemainingDatabase::class.java, "toDo_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
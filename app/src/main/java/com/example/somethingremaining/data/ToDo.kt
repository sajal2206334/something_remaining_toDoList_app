package com.example.somethingremaining.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "something_remaining")
data class ToDo(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    @ColumnInfo
    val title : String,
    @ColumnInfo
    val description : String,
    @ColumnInfo
    val date : String
    //@ColumnInfo
    //val isCompleted : Boolean = false
)

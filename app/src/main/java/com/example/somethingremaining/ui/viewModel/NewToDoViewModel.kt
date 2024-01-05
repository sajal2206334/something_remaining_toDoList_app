package com.example.somethingremaining.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.somethingremaining.data.ToDo
import com.example.somethingremaining.data.ToDoRepository

class NewToDoViewModel(private val toDoRepository: ToDoRepository) : ViewModel() {

    var newToDoUiState by mutableStateOf(NewToDoUiState())
        private set

    fun updateUiState(newToDoDetails: NewToDoDetails) {
        newToDoUiState = NewToDoUiState(newToDoDetails = newToDoDetails, isvalidInput = validInput(newToDoDetails))
    }

    private fun validInput(uiState : NewToDoDetails = newToDoUiState.newToDoDetails) : Boolean {
        return with(uiState) {
            title.isNotBlank()
        }
    }

    suspend fun saveNewToDo() {
        if (validInput()) {
            toDoRepository.insertToDo(newToDoUiState.newToDoDetails.toToDo())
        }
    }
}

data class NewToDoUiState(
    val newToDoDetails: NewToDoDetails = NewToDoDetails(),
    val isvalidInput : Boolean = false
)

data class NewToDoDetails(
    val id : Int = 0,
    val title : String = "",
    val description : String = "",
    val date : String = ""
    //val isCompleted : Boolean = false
)

fun NewToDoDetails.toToDo() : ToDo = ToDo(
    id = id,
    title = title,
    description = description,
    date = date
    //isCompleted = isCompleted
)

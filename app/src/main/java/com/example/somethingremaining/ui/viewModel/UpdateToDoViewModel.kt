package com.example.somethingremaining.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.somethingremaining.data.ToDo
import com.example.somethingremaining.data.ToDoRepository
import com.example.somethingremaining.ui.screen.ToDoUpdateDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UpdateToDoViewModel(
    savedStateHandle: SavedStateHandle,
    private val toDoRepository: ToDoRepository) : ViewModel() {

    private val toDoId : Int = checkNotNull(savedStateHandle[ToDoUpdateDestination.toDoIdArg])

    /*val uiState : StateFlow<UpdateToDoUiState> =
        toDoRepository.getItem(toDoId)
            .filterNotNull()
            .map { UpdateToDoUiState(
                toDoDetails = it.totoDoDetails()
            ) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2_000L),
                initialValue = UpdateToDoUiState()
            )*/

    var updateUiState by mutableStateOf(UpdateToDoUiState())
        private set

    init {
        viewModelScope.launch {
            updateUiState = toDoRepository.getItem(toDoId)
                .filterNotNull()
                .first()
                .toUpdateToDoUiState()
        }
    }

    fun updateToDoUiState(toDoDetails: ToDoDetails) {
        updateUiState = UpdateToDoUiState(toDoDetails)
    }

    suspend fun updateToDo() {
        toDoRepository.updateToDo(updateUiState.toDoDetails.totoDo())
    }

}

data class UpdateToDoUiState(
    val toDoDetails : ToDoDetails = ToDoDetails()
)

data class ToDoDetails(
    val id : Int = 0,
    val title : String = "",
    val description : String = "",
    val date : String = ""
    //val isCompleted : Boolean = false
)

fun ToDo.totoDoDetails() : ToDoDetails = ToDoDetails(
    id = id,
    title = title,
    description = description,
    date = date
    //isCompleted = isCompleted

)

fun ToDoDetails.totoDo() : ToDo = ToDo(
    id = id,
    title = title,
    description = description,
    date = date
    //isCompleted = isCompleted
)

fun ToDo.toUpdateToDoUiState() : UpdateToDoUiState = UpdateToDoUiState(
    toDoDetails = this.totoDoDetails()
)
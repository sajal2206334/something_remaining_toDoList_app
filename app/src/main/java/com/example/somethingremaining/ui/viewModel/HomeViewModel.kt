package com.example.somethingremaining.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.somethingremaining.data.ToDo
import com.example.somethingremaining.data.ToDoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val toDoRepository: ToDoRepository) : ViewModel() {
    var homeUiState : StateFlow<HomeUiState> =
        toDoRepository.getAllToDo().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(Timeout),
                initialValue = HomeUiState()
            )

    companion object {
        private const val Timeout = 2_000L
    }

    suspend fun deleteToDo(toDo: ToDo) {
        toDoRepository.deleteToDo(toDo)
    }

    fun updateUiState(toDo: List<ToDo>) {
        HomeUiState(toDoList = toDo)
    }

    /*suspend fun updateIsCompleted(toDo: ToDo) {
        viewModelScope.launch {
            toDoRepository.updateIsCompleted(toDo.id, !toDo.isCompleted)
        }
    }*/

    /*fun updateState(toDo: ToDo) {
        viewModelScope.launch {
            toDoRepository.updateState(toDo)
        }
    }*/
}

data class HomeUiState(
    val toDoList: List<ToDo> = emptyList()
)
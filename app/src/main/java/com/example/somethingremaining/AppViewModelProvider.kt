package com.example.somethingremaining

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.somethingremaining.ui.viewModel.HomeViewModel
import com.example.somethingremaining.ui.viewModel.NewToDoViewModel
import com.example.somethingremaining.ui.viewModel.UpdateToDoViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer { HomeViewModel(
            toDoApplication().container.toDoRepository
        ) }

        initializer { NewToDoViewModel(
            toDoApplication().container.toDoRepository
        ) }

        initializer { UpdateToDoViewModel(
            this.createSavedStateHandle(),
            toDoApplication().container.toDoRepository
        ) }
    }
}

fun CreationExtras.toDoApplication() : SomethingRemianingApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SomethingRemianingApplication)
package com.example.somethingremaining.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.somethingremaining.AppTopBar
import com.example.somethingremaining.AppViewModelProvider
import com.example.somethingremaining.navigation.NavigationDestination
import com.example.somethingremaining.ui.viewModel.NewToDoDetails
import com.example.somethingremaining.ui.viewModel.NewToDoUiState
import com.example.somethingremaining.ui.viewModel.NewToDoViewModel
import kotlinx.coroutines.launch


object NewToDoDestination : NavigationDestination {
    override val route = "new_toDo"
    override val topBarTitle = "Add New To Do"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewToDoScreen(
    navigateBack : () -> Unit,
    viewModel: NewToDoViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            AppTopBar(
                title = NewToDoDestination.topBarTitle,
                canNavigateBack = true,
                navigateBack = navigateBack)
        }
    ) {innerPadding ->
        NewToDoScreenLayout(
            newToDoUiState = viewModel.newToDoUiState,
            onvalueChange = viewModel::updateUiState,
            onSave = { coroutineScope.launch {
                viewModel.saveNewToDo()
                navigateBack()
            } },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth())
    }
}

@Composable
fun NewToDoScreenLayout(
    newToDoUiState: NewToDoUiState,
    onvalueChange: (NewToDoDetails) -> Unit,
    onSave : () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(top = 100.dp,
            start = 12.dp,
            end = 12.dp)
    ) {
        ToDoEntryDisplay(
            newToDoDetails = newToDoUiState.newToDoDetails,
            onvalueChange = onvalueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = onSave,
            enabled = newToDoUiState.isvalidInput,
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "Save")
        }
    }
}

@Composable
fun ToDoEntryDisplay(
    newToDoDetails: NewToDoDetails,
    onvalueChange : (NewToDoDetails) -> Unit = {},
    modifier: Modifier = Modifier,
    validClick : Boolean = true
) {
    Column(modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = newToDoDetails.title,
            onValueChange = { onvalueChange(newToDoDetails.copy(title = it))},
            label = { Text(text = "Enter Title*") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = validClick,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = newToDoDetails.description,
            onValueChange = { onvalueChange(newToDoDetails.copy(description = it))},
            label = { Text(text = "Enter Description") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = newToDoDetails.date,
            onValueChange = { onvalueChange(newToDoDetails.copy(date = it))},
            label = { Text(text = "Enter Date") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )
    }
}
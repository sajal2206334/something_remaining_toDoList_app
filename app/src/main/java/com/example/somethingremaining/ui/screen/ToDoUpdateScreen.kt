package com.example.somethingremaining.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.somethingremaining.data.ToDo
import com.example.somethingremaining.navigation.NavigationDestination
import com.example.somethingremaining.ui.viewModel.UpdateToDoUiState
import com.example.somethingremaining.ui.viewModel.UpdateToDoViewModel
import com.example.somethingremaining.ui.viewModel.totoDo
import com.example.somethingremaining.ui.viewModel.totoDoDetails
import kotlinx.coroutines.launch

object ToDoUpdateDestination : NavigationDestination {
    override val route = "update_screen"
    override val topBarTitle = "Update Your To Do"
    const val toDoIdArg = "toDoId"
    val routeWithArgs = "$route/{$toDoIdArg}"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoUpdateScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateToDoViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateBack : () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            AppTopBar(
                title = ToDoUpdateDestination.topBarTitle,
                canNavigateBack = true,
                navigateBack = navigateBack
            )
        },
        modifier = Modifier
    ) {innerPadding ->
        ToDoUpdateScreenLayout(
            updateToDoUiState = viewModel.updateUiState,
            onvalueChange = { viewModel.updateToDoUiState(it.totoDoDetails()) },
            onSave = { coroutineScope.launch {
                viewModel.updateToDo()
                navigateBack()
            } },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ToDoUpdateScreenLayout(
    updateToDoUiState: UpdateToDoUiState,
    onvalueChange: (ToDo) -> Unit,
    onSave : () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(top = 100.dp,
            start = 12.dp,
            end = 12.dp)
    ) {
        ToDoUpdateDisplay(
            toDo = updateToDoUiState.toDoDetails.totoDo(),
            onvalueChange = onvalueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = onSave,
            enabled = true,
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "Update")
        }
    }
}

@Composable
fun ToDoUpdateDisplay(
    toDo: ToDo,
    onvalueChange : (ToDo) -> Unit = {},
    modifier: Modifier = Modifier,
    validClick : Boolean = true
) {
    Column(modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = toDo.title,
            onValueChange = { onvalueChange(toDo.copy(title = it))},
            label = { Text(text = "Update Title*") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = validClick,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = toDo.description,
            onValueChange = { onvalueChange(toDo.copy(description = it))},
            label = { Text(text = "Update Description") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = toDo.date,
            onValueChange = { onvalueChange(toDo.copy(date = it))},
            label = { Text(text = "Update Date") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )
    }
}
package com.example.somethingremaining.ui.screen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.somethingremaining.AppTopBar
import com.example.somethingremaining.AppViewModelProvider
import com.example.somethingremaining.R
import com.example.somethingremaining.data.ToDo
import com.example.somethingremaining.navigation.NavigationDestination
import com.example.somethingremaining.ui.theme.SomethingRemainingTheme
import com.example.somethingremaining.ui.viewModel.HomeViewModel
import kotlinx.coroutines.launch


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val topBarTitle = "Something Remaining"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToNewToDo : () -> Unit,
    navigateToUpdate : (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val localFocusManager = LocalFocusManager.current

    BackHandler(
        enabled = true,
        onBack = { backPressedDispatcher?.onBackPressed()
        }
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .clickable { localFocusManager.clearFocus() },
        topBar = { AppTopBar(
            title = HomeDestination.topBarTitle,
            canNavigateBack = false,
            scrollBehavior = scrollBehavior
        )},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToNewToDo() },
                shape = CircleShape,
                modifier = Modifier.padding(20.dp),
                containerColor = MaterialTheme.colorScheme.primary) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = null)
            }
        }
    ) {innerPadding ->
        HomeScreenLayout(toDoList = homeUiState.toDoList,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onDelete = { coroutineScope.launch {
                viewModel.deleteToDo(it)
            }},
            navigateToUpdate = navigateToUpdate
            //isComplete = { isChecked ->
              //  viewModel.updateState()
            //}
        )
    }
}

@Composable
fun HomeScreenLayout(//isComplete: (ToDo) -> Unit,
                     navigateToUpdate: (Int) -> Unit,onDelete: (ToDo) -> Unit,toDoList: List<ToDo>, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 80.dp, start = 8.dp, end = 8.dp)
    ) {
        var searchQuery by rememberSaveable { mutableStateOf("") }
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(LocalContext.current)
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
            if (spokenText != null) {
                searchQuery = spokenText
            }
        }

        if (toDoList.isEmpty()) {
            Text(text = "All caught up! Take a break and celebrate your accomplishments!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Tap + to add",
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(52.dp))
            Image(
                painter = painterResource(R.drawable.no_todo),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 300.dp, height = 300.dp)
                    .alpha(0.3f))
        } else {
            val context = LocalContext.current
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it },
                label = { Text(text = "Search By Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                leadingIcon = { Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = {
                        if (hasRecordAudioPermission(context)) {
                            launcher.launch(intent)
                        } else {
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_mic_24),
                            contentDescription = null)
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
            )
            Spacer(modifier = Modifier.height(24.dp))
            ToDoList(
                toDoList = toDoList.filter { it.title.contains(searchQuery, ignoreCase = true) },
                onDelete = onDelete,
                navigateToUpdate = { navigateToUpdate(it.id) },
                modifier = Modifier.padding(horizontal = 8.dp)
                //isComplete = isComplete
            )
        }
    }
}


fun hasRecordAudioPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
}


@Composable
fun ToDoList(//isComplete: (ToDo) -> Unit,
             navigateToUpdate: (ToDo) -> Unit,onDelete: (ToDo) -> Unit,toDoList: List<ToDo>,modifier: Modifier = Modifier) {
    LazyColumn(modifier = Modifier) {
        items(items = toDoList, key = { it.id }) {toDo ->
            ToDoSingleCard(toDo = toDo,
                modifier = Modifier
                    .padding(3.dp)
                    .clickable { navigateToUpdate(toDo) },
                onDelete = onDelete,
                navigateToUpdate = { navigateToUpdate(toDo) },
                /*isComplete = isComplete*/)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ToDoSingleCard(//isComplete : (ToDo) -> Unit,
                   navigateToUpdate : (ToDo) -> Unit,toDo: ToDo,onDelete : (ToDo) -> Unit,modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp)
            .clickable { navigateToUpdate(toDo) }
            .height(84.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        var deleteConfirmation by rememberSaveable { mutableStateOf(false) }
        Column(modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            //Checkbox(
              //  checked = toDo.isCompleted,
               // onCheckedChange = { isComplete(toDo) })
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = toDo.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = toDo.date,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = toDo.description,
                    style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { deleteConfirmation = true}) {
                    Icon(imageVector = Icons.Default.Delete,
                        contentDescription = null)
                }
                if (deleteConfirmation) {
                    DeleteBox(
                        onConfirm = {
                                    deleteConfirmation = true
                            onDelete(toDo)
                        },
                        onCancel = {
                            deleteConfirmation = false
                        },
                        modifier = Modifier.padding(12.dp))
                }
            }
        }
    }
}

@Composable
fun DeleteBox(
    onConfirm : () -> Unit,
    onCancel : () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = {},
        title = { Text(text = "Attention")},
        text = { Text(text = "Are you sure you want to delete?")},
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(text = "cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "delete")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ToDoSingleCardPreview() {
    SomethingRemainingTheme {
        ToDoSingleCard(
            toDo = ToDo(
                1,
                "Learn Coding",
                "Learn coding with android development official website",
                "12 Feb"
            ),
            onDelete = {},
            navigateToUpdate = {}
            //isComplete = {}
        )
    }
}

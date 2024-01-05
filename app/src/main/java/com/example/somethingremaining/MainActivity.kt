package com.example.somethingremaining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.somethingremaining.data.ToDo
import com.example.somethingremaining.ui.screen.HomeScreen
import com.example.somethingremaining.ui.screen.LoginScreen
import com.example.somethingremaining.ui.screen.NewToDoScreen
import com.example.somethingremaining.ui.screen.SplashScreen
import com.example.somethingremaining.ui.screen.ToDoUpdateScreen
import com.example.somethingremaining.ui.theme.SomethingRemainingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SomethingRemainingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SomethingRemainingApp()
                }
            }
        }
    }
}
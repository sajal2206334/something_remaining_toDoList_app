package com.example.somethingremaining.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.somethingremaining.R

@Composable
fun LoginScreen(modifier: Modifier = Modifier
    .background(Color(0xFFFB8500))) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                // Implement gradient with subtle wave pattern here
                Color(0xFFFB8500)
            )
    ) {
        // Center logo and title text
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Birdie",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .padding(vertical = 24.dp, horizontal = 32.dp)
    ) {
        Column {
            Text(
                text = "Sign In",
                color = Color(0xFF4285F4),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF4285F4))
            ) {
                Text(text = "Log In", color = Color.White)
            }
        }
    }
}
package com.example.somethingremaining.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.somethingremaining.R
import com.example.somethingremaining.navigation.NavigationDestination
import kotlinx.coroutines.delay

object SplashDestination : NavigationDestination {
    override val route = "splash"
    override val topBarTitle = "splash"
}
@Composable
fun SplashScreen(onTimeOut : () -> Unit,modifier: Modifier = Modifier) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xD8B28CE7))
    , Alignment.Center) {
        LaunchedEffect(Unit) {
            delay(2000)
            onTimeOut()
        }

        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.no_todo),
                contentDescription = null,
                modifier = Modifier.size(width = 320.dp, height = 320.dp)
                    .padding(end = 36.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "SOMETHING REMAINING",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
            )
        }
    }


}
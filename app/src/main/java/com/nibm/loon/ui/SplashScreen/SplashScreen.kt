package com.nibm.loon.ui.SplashScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nibm.loon.ui.loginScreen.LoginScreen
import kotlinx.coroutines.delay
import com.nibm.loon.R  // Add this import

class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreenUI {
                startActivity(Intent(this, LoginScreen::class.java))
                finish()
            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun PreviewSplashScreenUI() {
    SplashScreenUI ( onTimeout = {} )
}

@Composable
fun SplashScreenUI(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),  // Changed this line
            contentDescription = "Salon Logo",
            modifier = Modifier.size(200.dp)
        )
    }
}
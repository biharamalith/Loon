package com.nibm.loon.ui.loginScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.nibm.loon.R
import com.nibm.loon.ui.homeScreen.HomeScreen
import com.nibm.loon.ui.signUpScreen.SignUpScreen
import com.nibm.loon.ui.theme.Aeonik
import com.nibm.loon.ui.theme.DarkGreenColor
import com.nibm.loon.ui.theme.GothamBlack
import com.nibm.loon.ui.theme.GreenColor

class LoginScreen : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {
            LoginUI(
                onLogin = { email, password -> signIn(email, password) },
                onSignUp = {
                    startActivity(Intent(this, SignUpScreen::class.java))
                }
            )
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, HomeScreen::class.java))
            finish()
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(
                        baseContext,
                        "Authentication successful.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    startActivity(Intent(this, HomeScreen::class.java))
                    finish()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    companion object {
        private const val TAG = "EmailPassword"
    }
}

@Preview(
    showBackground = true,
    widthDp = 412,
    heightDp = 846,
    device = "spec:width=720dp,height=1480dp,dpi=280"
)
@Composable
fun PreviewLoginUI() {
    LoginUI(onLogin = { _, _ -> }, onSignUp = {})
}

@Composable
fun LoginUI(
    onLogin: (String, String) -> Unit,
    onSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_bg_removed),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 25.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(32.dp)
        ) {

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                enabled = !isLoading
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                enabled = !isLoading
            )

            Button(
                onClick = {
                    isLoading = true
                    onLogin(email, password)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(vertical = 8.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = GreenColor
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 16.dp
                ),
                shape = RoundedCornerShape(16.dp)

            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = MaterialTheme.colors.onPrimary)
                } else {
                    Text(
                        text ="LOGIN",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = GothamBlack,
                            fontWeight = FontWeight.Black,
                        ),
                        color = DarkGreenColor
                    )
                }
            }

            TextButton(
                onClick = onSignUp,
                enabled = !isLoading,
            ) {
                Text(
                    text = "Don't have an account? Sign up",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = Aeonik,
                        fontWeight = FontWeight.Normal,
                    ),
                    color = DarkGreenColor
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.background_4),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}
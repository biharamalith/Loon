package com.nibm.loon.ui.signUpScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nibm.loon.R
import com.nibm.loon.ui.loginScreen.LoginScreen
import com.nibm.loon.ui.theme.Aeonik
import com.nibm.loon.ui.theme.DarkGreenColor
import com.nibm.loon.ui.theme.GothamBlack
import com.nibm.loon.ui.theme.GreenColor

class SignUpScreen : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContent {
            var isLoading by remember { mutableStateOf(false) }

            SignUpUI(
                isLoading = isLoading,
                onSignUp = { firstName, lastName, city, district, email, password, onError ->
                    isLoading = true
                    signUpUser(
                        firstName, lastName, city, district, email, password,
                        onSignUpSuccess = {
                            isLoading = false
                            startActivity(Intent(this, LoginScreen::class.java))
                            finish()
                        },
                        onError = { error ->
                            isLoading = false
                            onError(error)
                        }
                    )
                }
            )
        }
    }

    private fun signUpUser(
        firstName: String,
        lastName: String,
        city: String,
        district: String,
        email: String,
        password: String,
        onSignUpSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    auth.signOut()
                    val user = task.result?.user
                    val uid = user?.uid

                    val userDetails = UserDetails(firstName, lastName, city, district, email)
                    uid?.let {
                        database.getReference("users").child(it).setValue(userDetails)
                            .addOnSuccessListener {
                                Toast.makeText(baseContext, "Sign-up successful. Please log in.", Toast.LENGTH_SHORT).show()
                                onSignUpSuccess()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error saving user details", e)
                                onError("Error saving user details: ${e.message}")
                            }
                    }
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    onError("Authentication failed: ${task.exception?.message}")
                }
            }
    }

    companion object {
        private const val TAG = "SignUpScreen"
    }
}

data class UserDetails(
    val firstName: String,
    val lastName: String,
    val city: String,
    val district: String,
    val email: String
)


@Preview(
    showBackground = true,
    widthDp = 412,
    heightDp = 846,
    device = "spec:width=720dp,height=1480dp,dpi=280"
)
@Composable
fun SignUpScreenPreview() {
    SignUpUI(
        isLoading = false,
        onSignUp = { firstName, lastName, city, district, email, password, onError ->
            // Mock sign up action, no actual logic here
            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                // Simulate success
                Log.d("SignUpPreview", "Mock sign-up successful")
            } else {
                // Simulate error
                onError("Please fill out all fields correctly")
            }
        }
    )
}


@Composable
fun SignUpUI(
    isLoading: Boolean,
    onSignUp: (
        firstName: String,
        lastName: String,
        city: String,
        district: String,
        email: String,
        password: String,
        onError: (String) -> Unit
    ) -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_4),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillWidth // Adjust this if necessary
        )
        if (isLoading) {
            CircularProgressIndicator(color = GreenColor)
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_text_2),
                    contentDescription = "Loon Logo",
                    modifier = Modifier
                        .height(100.dp)
                        .width(200.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.FillWidth
                )
                Text(
                    text = "Create Your Account",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = Aeonik,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    color = DarkGreenColor
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Gray.copy(alpha = 0.1f))
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = city,
                        onValueChange = { city = it },
                        label = { Text("City") },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = district,
                        onValueChange = { district = it },
                        label = { Text("District") },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f)
                    )
                }
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(17.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                errorMessage?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

                Button(
                    onClick = {
                        if (firstName.isEmpty() || lastName.isEmpty() || city.isEmpty() || district.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                            errorMessage = "Please fill out all fields."
                        } else if (password != confirmPassword) {
                            errorMessage = "Passwords do not match."
                        } else {
                            errorMessage = null
                            onSignUp(
                                firstName,
                                lastName,
                                city,
                                district,
                                email,
                                password
                            ) { errorMessage = it }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = GreenColor
                    )
                ) {
                    Text(
                        text = "SIGN UP",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = GothamBlack,
                            fontWeight = FontWeight.Black,
                        ),
                        color = DarkGreenColor
                    )
                }
            }
        }
    }
}
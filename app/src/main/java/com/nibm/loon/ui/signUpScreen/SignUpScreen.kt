package com.nibm.loon.ui.signUpScreen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpScreen : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContent {
            SignUpUI(
                onSignUpSuccess = {
                    Toast.makeText(this, "Sign Up Successful! Welcome!", Toast.LENGTH_SHORT).show()
                    finish()
                },
                onSignUpFailure = { message ->
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}


@Composable
fun SignUpUI(
    onSignUpSuccess: () -> Unit,
    onSignUpFailure: (String) -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Sign Up", style = MaterialTheme.typography.h4)

            OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") })
            OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") })
            OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("City") })
            OutlinedTextField(value = district, onValueChange = { district = it }, label = { Text("District") })
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    if (password == confirmPassword) {
                        checkAndSignUpUser(
                            email = email,
                            password = password,
                            firstName = firstName,
                            lastName = lastName,
                            city = city,
                            district = district,
                            onSignUpSuccess = {
                                onSignUpSuccess()
                                // Clear all fields upon success
                                firstName = ""
                                lastName = ""
                                city = ""
                                district = ""
                                email = ""
                                password = ""
                                confirmPassword = ""
                            },
                            onSignUpFailure = onSignUpFailure
                        )
                    } else {
                        onSignUpFailure("Passwords do not match.")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF61F879))
            ) {
                Text("Sign Up")
            }
        }
    }
}

fun checkAndSignUpUser(
    email: String,
    password: String,
    firstName: String,
    lastName: String,
    city: String,
    district: String,
    onSignUpSuccess: () -> Unit,
    onSignUpFailure: (String) -> Unit
) {
    FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val methods = task.result?.signInMethods
                if (methods.isNullOrEmpty()) {
                    // Email is available, proceed with sign up
                    createNewUser(email, password, firstName, lastName, city, district, onSignUpSuccess, onSignUpFailure)
                } else {
                    onSignUpFailure("An account with this email already exists.")
                }
            } else {
                onSignUpFailure("Error checking email: ${task.exception?.message}")
            }
        }
}

fun createNewUser(
    email: String,
    password: String,
    firstName: String,
    lastName: String,
    city: String,
    district: String,
    onSignUpSuccess: () -> Unit,
    onSignUpFailure: (String) -> Unit
) {
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = task.result?.user ?: return@addOnCompleteListener onSignUpFailure("User creation failed.")
                val uid = user.uid

                val userMap = mapOf(
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "city" to city,
                    "district" to district,
                    "email" to email
                )

                Firebase.database.reference.child("users").child(uid).setValue(userMap)
                    .addOnCompleteListener { dbTask ->
                        if (dbTask.isSuccessful) {
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName("$firstName $lastName")
                                .build()

                            user.updateProfile(profileUpdates)
                                .addOnCompleteListener { profileTask ->
                                    if (profileTask.isSuccessful) {
                                        onSignUpSuccess()
                                    } else {
                                        onSignUpFailure("Failed to update profile: ${profileTask.exception?.message}")
                                    }
                                }
                        } else {
                            onSignUpFailure("Failed to save user data: ${dbTask.exception?.message}")
                        }
                    }
            } else {
                onSignUpFailure("Sign Up Failed: ${task.exception?.message}")
            }
        }
}

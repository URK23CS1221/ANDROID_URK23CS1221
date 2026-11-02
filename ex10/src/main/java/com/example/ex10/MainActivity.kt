package com.example.ex10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AuthScreen()
                }
            }
        }
    }
}

@Composable
fun AuthScreen() {
    val auth = remember {
        try {
            FirebaseAuth.getInstance()
        } catch (_: Exception) {
            null
        }
    }

    var user by remember { mutableStateOf(auth?.currentUser) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    if (auth != null) {
        DisposableEffect(auth) {
            val listener = FirebaseAuth.AuthStateListener {
                user = it.currentUser
            }
            auth.addAuthStateListener(listener)
            onDispose {
                auth.removeAuthStateListener(listener)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (user == null) {
            Text(
                text = "Firebase Authentication",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        message = "Enter email and password"
                    } else if (auth == null) {
                        message = "Firebase Auth not configured (google-services.json required)"
                    } else {
                        auth.createUserWithEmailAndPassword(email.trim(), password)
                            .addOnSuccessListener {
                                message = "Registration successful"
                            }
                            .addOnFailureListener {
                                message = it.localizedMessage ?: "Registration failed"
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        message = "Enter email and password"
                    } else if (auth == null) {
                        message = "Firebase Auth not configured (google-services.json required)"
                    } else {
                        auth.signInWithEmailAndPassword(email.trim(), password)
                            .addOnSuccessListener {
                                message = ""
                            }
                            .addOnFailureListener {
                                message = it.localizedMessage ?: "Login failed"
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(text = message)
        } else {
            Text(
                text = "Protected Screen",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Welcome: ${user?.email}")
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    auth?.signOut()
                    message = "Signed out successfully"
                }
            ) {
                Text("Sign Out")
            }
        }
    }
}

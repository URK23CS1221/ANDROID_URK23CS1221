package com.example.ex04

import android.net.Uri
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(onNavigateToDetails = { name, regNo, dept, gender ->
                val encodedName = Uri.encode(name)
                val encodedRegNo = Uri.encode(regNo)
                val encodedDept = Uri.encode(dept)
                val encodedGender = Uri.encode(gender)
                navController.navigate("details/$encodedName/$encodedRegNo/$encodedDept/$encodedGender")
            })
        }
        composable("details/{name}/{regNo}/{dept}/{gender}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val regNo = backStackEntry.arguments?.getString("regNo") ?: ""
            val dept = backStackEntry.arguments?.getString("dept") ?: ""
            val gender = backStackEntry.arguments?.getString("gender") ?: ""

            DetailsScreen(
                name = name,
                registerNumber = regNo,
                department = dept,
                gender = gender,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun HomeScreen(onNavigateToDetails: (String, String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var regNo by remember { mutableStateOf("") }
    var dept by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Student Data Input", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = regNo,
            onValueChange = { regNo = it },
            label = { Text("Register Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = dept,
            onValueChange = { dept = it },
            label = { Text("Department") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Gender") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onNavigateToDetails(name, regNo, dept, gender) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SUBMIT & NAVIGATE")
        }
    }
}

@Composable
fun DetailsScreen(
    name: String,
    registerNumber: String,
    department: String,
    gender: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Student Details Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(20.dp))

        Text("Name: $name", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Register Number: $registerNumber", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Department: $department", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Gender: $gender", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("BACK")
        }
    }
}

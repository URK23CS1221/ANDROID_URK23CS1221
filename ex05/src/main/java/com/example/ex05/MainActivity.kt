package com.example.ex05

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class Student(
    val name: String,
    val registerNumber: String,
    val department: String,
    val gender: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sampleStudents = listOf(
            Student("Alex Mercer", "URK23CS1001", "Computer Science", "Male"),
            Student("Brianna Taylor", "URK23CS1002", "Information Technology", "Female"),
            Student("Charlie Brown", "URK23CS1003", "Cyber Security", "Male"),
            Student("Diana Prince", "URK23CS1004", "Artificial Intelligence", "Female"),
            Student("Ethan Hunt", "URK23CS1005", "Data Science", "Male")
        )

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    StudentApp(students = sampleStudents)
                }
            }
        }
    }
}

@Composable
fun StudentApp(students: List<Student>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "students") {
        composable("students") {
            StudentListScreen(students) { student ->
                navController.navigate(
                    "details/${Uri.encode(student.name)}/" +
                            "${Uri.encode(student.registerNumber)}/" +
                            "${Uri.encode(student.department)}/" +
                            Uri.encode(student.gender)
                )
            }
        }
        composable("details/{name}/{registerNumber}/{department}/{gender}") { entry ->
            StudentDetailsScreen(
                name = entry.arguments?.getString("name") ?: "",
                registerNumber = entry.arguments?.getString("registerNumber") ?: "",
                department = entry.arguments?.getString("department") ?: "",
                gender = entry.arguments?.getString("gender") ?: "",
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun StudentListScreen(
    students: List<Student>,
    onStudentClick: (Student) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Smart Student Directory",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(students) { student ->
                StudentItem(student = student, onClick = { onStudentClick(student) })
            }
        }
    }
}

@Composable
fun StudentItem(student: Student, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = student.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Register Number: ${student.registerNumber}")
            Text(text = "Department: ${student.department}")
        }
    }
}

@Composable
fun StudentDetailsScreen(
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
        Text("Student Details", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        Text("Name: $name", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Register Number: $registerNumber", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Department: $department", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text("Gender: $gender", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}

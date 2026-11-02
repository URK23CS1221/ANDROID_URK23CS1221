package com.example.android_urk23cs1221

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ExperimentInfo(
    val number: String,
    val title: String,
    val folder: String,
    val description: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ExperimentDashboardScreen()
                }
            }
        }
    }
}

@Composable
fun ExperimentDashboardScreen() {
    val experiments = listOf(
        ExperimentInfo("Ex 1", "Basic Views & UI Layouts", "ex01", "Kotlin Compose basic Views (Text, OutlinedTextField, Button)"),
        ExperimentInfo("Ex 2", "Activity Interaction & Animation", "ex02", "Food Ordering app with Explicit Intents & Compose Animations"),
        ExperimentInfo("Ex 3", "Fragment-Compose Interoperability", "ex03", "Employee Announcement app using ComposeView in Fragment"),
        ExperimentInfo("Ex 4", "Navigate with Data Between Screens", "ex04", "Navigation Compose passing Student data between screens"),
        ExperimentInfo("Ex 5", "RecyclerView-like List in Compose", "ex05", "LazyColumn list of students with Card items & detail view"),
        ExperimentInfo("Ex 6", "Room Database & Preferences DataStore", "ex06", "Quick Notes app with Room persistence & DataStore Dark Mode"),
        ExperimentInfo("Ex 7", "Background Tasks with WorkManager", "ex07", "WorkManager background task execution logged to Logcat"),
        ExperimentInfo("Ex 8", "REST API with JSON Parsing", "ex08", "HTTP GET request to JSONPlaceholder API on Dispatchers.IO"),
        ExperimentInfo("Ex 9", "Firebase Cloud Firestore", "ex09", "Cloud Firestore real-time CRUD and LazyColumn list"),
        ExperimentInfo("Ex 10", "Firebase Authentication", "ex10", "Email/Password sign up, login, & protected screen access"),
        ExperimentInfo("Ex 11", "Testing & Debugging (JUnit & Compose Test)", "ex11", "Unit testing NoteValidator & Compose UI testing NoteScreen")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "24CS2021 - Mobile App Dev",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "URK23CS1221 - 11 Multi-Module Experiments",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(experiments) { exp ->
                ExperimentCard(exp)
            }
        }
    }
}

@Composable
fun ExperimentCard(exp: ExperimentInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${exp.number}: ${exp.title}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Folder Module: /${exp.folder}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = exp.description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

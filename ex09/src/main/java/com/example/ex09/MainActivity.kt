package com.example.ex09

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

data class StudentRecord(
    val id: String = "",
    val name: String = "",
    val regNo: String = ""
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    FirestoreScreen()
                }
            }
        }
    }
}

@Composable
fun FirestoreScreen() {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var regNo by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var records by remember { mutableStateOf<List<StudentRecord>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    val db = remember {
        try {
            FirebaseFirestore.getInstance()
        } catch (_: Exception) {
            null
        }
    }

    LaunchedEffect(db) {
        db?.collection("students")?.addSnapshotListener { snapshot, error ->
            if (error != null) {
                statusMessage = "Error listening to records: ${error.message}"
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val list = snapshot.documents.map { doc ->
                    StudentRecord(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        regNo = doc.getString("regNo") ?: ""
                    )
                }
                records = list
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Firestore Student Records",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it; statusMessage = null },
            label = { Text("Student Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = regNo,
            onValueChange = { regNo = it; statusMessage = null },
            label = { Text("Register Number") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        Button(
            onClick = {
                if (name.isBlank() || regNo.isBlank()) {
                    statusMessage = "Please enter both name and register number."
                    return@Button
                }
                if (db == null) {
                    statusMessage = "Firestore is not configured properly (place google-services.json in module)."
                    return@Button
                }
                isLoading = true
                val recordData = hashMapOf(
                    "name" to name.trim(),
                    "regNo" to regNo.trim(),
                    "timestamp" to System.currentTimeMillis()
                )
                db.collection("students")
                    .add(recordData)
                    .addOnSuccessListener {
                        isLoading = false
                        statusMessage = "Record saved successfully!"
                        name = ""
                        regNo = ""
                        Toast.makeText(context, "Added to Firestore", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        isLoading = false
                        statusMessage = "Failed to save: ${e.localizedMessage}"
                    }
            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(if (isLoading) "Saving..." else "Add Record to Firestore")
        }

        if (statusMessage != null) {
            Text(
                text = statusMessage!!,
                color = if (statusMessage!!.contains("successfully")) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Text(
            text = "Saved Records (${records.size})",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        if (records.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No records found in Firestore.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(records, key = { it.id }) { record ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = record.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Reg No: ${record.regNo}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

package com.example.ex06

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = NoteDatabase.getDatabase(this).noteDao()
        val dataStore = DataStoreManager(this)

        setContent {
            val darkMode by dataStore.darkMode.collectAsState(initial = false)
            MaterialTheme(
                colorScheme = if (darkMode) darkColorScheme() else lightColorScheme()
            ) {
                QuickNotesScreen(dao = dao, dataStore = dataStore, darkMode = darkMode)
            }
        }
    }
}

@Composable
fun QuickNotesScreen(
    dao: NoteDao,
    dataStore: DataStoreManager,
    darkMode: Boolean
) {
    val notes by dao.getAll().collectAsState(initial = emptyList())
    var noteText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Dark mode")
            Switch(
                checked = darkMode,
                onCheckedChange = { enabled ->
                    scope.launch {
                        dataStore.setDarkMode(enabled)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Quick Notes",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                label = { Text("New note") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    val text = noteText.trim()
                    if (text.isNotEmpty()) {
                        scope.launch {
                            dao.insert(Note(text = text))
                            noteText = ""
                        }
                    }
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (notes.isEmpty()) {
            Text("No notes yet. Add your first note.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(notes, key = { it.id }) { note ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = note.text,
                                modifier = Modifier.weight(1f)
                            )
                            TextButton(
                                onClick = {
                                    scope.launch {
                                        dao.delete(note)
                                    }
                                }
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}

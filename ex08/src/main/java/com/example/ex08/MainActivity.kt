package com.example.ex08

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ApiScreen()
                }
            }
        }
    }
}

@Composable
fun ApiScreen() {
    var result by remember { mutableStateOf("Click Fetch Data to load API output") }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "REST API Demo",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            enabled = !loading,
            onClick = {
                scope.launch {
                    loading = true
                    result = try {
                        withContext(Dispatchers.IO) {
                            val connection = URL("https://jsonplaceholder.typicode.com/posts/1")
                                .openConnection() as HttpURLConnection
                            try {
                                connection.requestMethod = "GET"
                                connection.connectTimeout = 10000
                                connection.readTimeout = 10000
                                if (connection.responseCode != 200) {
                                    error("HTTP ${connection.responseCode}")
                                }
                                val json = connection.inputStream.bufferedReader().use { it.readText() }
                                val obj = JSONObject(json)
                                "User ID: ${obj.getInt("userId")}\n\n" +
                                        "Post ID: ${obj.getInt("id")}\n\n" +
                                        "Title: ${obj.getString("title")}\n\n" +
                                        "Body: ${obj.getString("body")}"
                            } finally {
                                connection.disconnect()
                            }
                        }
                    } catch (e: Exception) {
                        "Failed to fetch data: ${e.message}"
                    }
                    loading = false
                }
            }
        ) {
            Text("Fetch Data")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(12.dp))
        }

        Text(
            text = result,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}

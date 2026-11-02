package com.example.ex02

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    FoodMenuScreen(
                        onOrderClick = { foodName, foodPrice ->
                            val intent = Intent(this, OrderActivity::class.java).apply {
                                putExtra("food_name", foodName)
                                putExtra("food_price", foodPrice)
                            }
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FoodMenuScreen(onOrderClick: (String, String) -> Unit) {
    val items = listOf(
        "Burger" to "₹150",
        "Pizza" to "₹300",
        "Sandwich" to "₹100",
        "Pasta" to "₹200"
    )
    var selectedItem by remember { mutableStateOf(items[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "FOOD ORDERING APP",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Select Your Food",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        items.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedItem = item }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (selectedItem == item),
                    onClick = { selectedItem = item }
                )
                Text(
                    text = "${item.first} - ${item.second}",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onOrderClick(selectedItem.first, selectedItem.second) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("ORDER NOW")
        }
    }
}

package com.example.ex02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class OrderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val foodName = intent.getStringExtra("food_name") ?: "Unknown Food"
        val foodPrice = intent.getStringExtra("food_price") ?: "₹0"

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    OrderConfirmationScreen(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        onBackToMenu = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun OrderConfirmationScreen(
    foodName: String,
    foodPrice: String,
    onBackToMenu: () -> Unit
) {
    var titleVisible by remember { mutableStateOf(false) }
    var detailsVisible by remember { mutableStateOf(false) }
    var thanksVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        titleVisible = true
        delay(300)
        detailsVisible = true
        delay(300)
        thanksVisible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = titleVisible,
            enter = fadeIn() + slideInVertically()
        ) {
            Text(
                text = "ORDER CONFIRMED!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedVisibility(
            visible = detailsVisible,
            enter = fadeIn() + scaleIn()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Item: $foodName",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Price: $foodPrice",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedVisibility(
            visible = thanksVisible,
            enter = fadeIn()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Thank you for your order!",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onBackToMenu,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("BACK TO MENU")
                }
            }
        }
    }
}

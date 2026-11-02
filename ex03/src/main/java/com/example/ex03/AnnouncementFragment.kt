package com.example.ex03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment

enum class AnnouncementType { PLAIN_TEXT, URGENT_ALERT, GENERAL_INFO }

data class Announcement(
    val id: Int,
    val title: String,
    val content: String,
    val type: AnnouncementType
)

class AnnouncementFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        AnnouncementScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun AnnouncementScreen() {
    val announcements = listOf(
        Announcement(1, "Town Hall Meeting", "All hands meeting this Friday at 3 PM in Auditorium.", AnnouncementType.GENERAL_INFO),
        Announcement(2, "URGENT: Server Maintenance", "System down for maintenance tonight 11 PM to 2 AM.", AnnouncementType.URGENT_ALERT),
        Announcement(3, "Holiday Schedule", "Office will remain closed on upcoming Monday for Public Holiday.", AnnouncementType.PLAIN_TEXT)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Employee Announcements",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(announcements) { item ->
                AnnouncementItem(item)
            }
        }
    }
}

@Composable
fun AnnouncementItem(announcement: Announcement) {
    val containerColor = when (announcement.type) {
        AnnouncementType.URGENT_ALERT -> Color(0xFFFFEBEE)
        AnnouncementType.GENERAL_INFO -> Color(0xFFE3F2FD)
        AnnouncementType.PLAIN_TEXT -> Color(0xFFF5F5F5)
    }

    val headerColor = when (announcement.type) {
        AnnouncementType.URGENT_ALERT -> Color(0xFFD32F2F)
        AnnouncementType.GENERAL_INFO -> Color(0xFF1976D2)
        AnnouncementType.PLAIN_TEXT -> Color(0xFF424242)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = announcement.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = headerColor
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = announcement.content,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}

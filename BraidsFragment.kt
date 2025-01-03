package com.nibm.loon.ui.homeScreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.database.FirebaseDatabase
import com.nibm.loon.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BraidsFragment() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top Search and Filter Section
        TopBar()

        Spacer(modifier = Modifier.height(8.dp))

        // List of Salons
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(getSampleSalons()) { salon ->
                SalonCard(salon)
            }
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Search Bar
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search") },
            modifier = Modifier.weight(1f),
            singleLine = true
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Date Filter Button
        Button(onClick = { /* Open Date Picker */ }) {
            Text("When?")
        }
    }
}

@Composable
fun SalonCard(salon: Salon) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        // Salon Details
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Salon Image (Profile)
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your image
                contentDescription = "Salon Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Salon Info
            Column {
                Text(text = salon.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = salon.distance, color = Color.Gray, fontSize = 14.sp)
                Text(text = "${salon.rating} ⭐ (${salon.reviews} reviews)", color = Color.Gray, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Image Carousel
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(salon.images) { image ->
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your image
                    contentDescription = "Service Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Service Buttons
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { /* View All Services */ }) {
                Text("See All Services")
            }

            Button(onClick = { /* Book Specific Service */ }) {
                Text(salon.serviceDetails)
            }
        }
    }
}

// Sample Data Model
data class Salon(
    val name: String,
    val distance: String,
    val rating: Float,
    val reviews: Int,
    val images: List<Int>,
    val serviceDetails: String
)

// Sample Data
fun getSampleSalons(): List<Salon> {
    return listOf(
        Salon(
            name = "Michelle Marks",
            distance = "22.7 mi",
            rating = 5.0f,
            reviews = 17,
            images = listOf(R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground),
            serviceDetails = "Box Braids - 285 Mins - $150"
        ),
        Salon(
            name = "Nisha",
            distance = "39.7 mi",
            rating = 5.0f,
            reviews = 19,
            images = listOf(R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_foreground),
            serviceDetails = "Kid's Cornrow Braids - 240 Mins"
        )
    )
}

package com.nibm.loon.ui.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nibm.loon.R

@Composable
fun MassageTherapyFragment() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box(
            modifier = Modifier
                .size(120.dp) // Set size of the box
                .clip(RoundedCornerShape(10.dp)) // Add rounded corners
                .background(Color.LightGray), // Fallback background color
            contentAlignment = Alignment.Center
        ) {
            // Replace with your image resource for massage therapy
            Image(
                painter = painterResource(id = R.drawable.massagetherapy), // Replace with your image resource
                contentDescription = "Massage Therapy",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp)) // Space between image and text
        Text(
            text = "Massage Therapy",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
package com.nibm.loon.ui.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nibm.loon.R

@Preview(showBackground = true)
@Composable
fun PreviewHomeFragment() {
    HomeFragment()
}

@Composable
fun HomeFragment() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.logo_text),
            contentDescription = "Loon Logo",
            modifier = Modifier
                .height(64.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        // Header text
        Text(
            text = "Discover & Book Local Hair Professionals",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search bar
        var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(48.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                singleLine = true,
            )
        }

        // "Find pros by service" text
        Text(
            text = "Find pros by service",
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Service grid
        val services = listOf(
            "Braids", "Natural Hair", "Haircut", "Men's Haircut",
            "Locs", "Slick Press", "Weaves", "Eyelashes", "Nails"
        )
        val columns = 3
        val rows = (services.size + columns - 1) / columns

        Column {
            for (row in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (col in 0 until columns) {
                        val index = row * columns + col
                        if (index < services.size) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                                    .clickable { },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = services[index], textAlign = TextAlign.Center)
                            }
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

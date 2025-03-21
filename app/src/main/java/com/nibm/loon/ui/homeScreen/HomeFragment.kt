package com.nibm.loon.ui.homeScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nibm.loon.R



@Composable
fun HomeFragment(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState()), // Enable scrolling
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val customFont = FontFamily(
            Font(R.font.londonbridgefontfamily)
        )

        Text(
            text = "LOON",
            fontSize = 50.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xff006400),
            fontFamily = customFont,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x80DAF7DC))
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Discover and Book",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF8B6A4D),
                    fontFamily = customFont,
                    modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
                )

                Text(
                    text = "Local Beauty Professionals",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF8B6A4D),
                    fontFamily = customFont,
                    modifier = Modifier.padding(top = 5.dp, bottom = 26.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x80DAF7DC))
                .padding(vertical = 30.dp)
        ) {
            GridLayout(navController)
        }

        Box(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth()
                .aspectRatio(2f)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.LightGray)
        ) {
            Image(
                painter = painterResource(id = R.drawable.loonstyle),
                contentDescription = "Your Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun GridLayout(navController: NavHostController) {
    Column(verticalArrangement = Arrangement.spacedBy(27.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ServiceBox("Hair Cutting", R.drawable.haircutting, navController, "hairCutting")
            Spacer(modifier = Modifier.width(22.dp))
            ServiceBox("Braids", R.drawable.braids, navController, "braids")
            Spacer(modifier = Modifier.width(22.dp))
            ServiceBox("Piercing", R.drawable.pircing, navController, "piercing")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ServiceBox("Facial Treatment", R.drawable.facialreatment, navController, "facialTreatment")
            Spacer(modifier = Modifier.width(22.dp))
            ServiceBox("Eye lashes", R.drawable.eyelashes, navController, "eyelashes")
            Spacer(modifier = Modifier.width(22.dp))
            ServiceBox("Massage Therapy", R.drawable.massagetherapy, navController, "massageTherapy")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ServiceBox("Nails", R.drawable.nails, navController, "nails")
            Spacer(modifier = Modifier.width(22.dp))
            ServiceBox("Locs", R.drawable.locs, navController, "locs")
            Spacer(modifier = Modifier.width(22.dp))
            ServiceBox("Slick Press", R.drawable.slickpres, navController, "slickpress")
        }
    }
}

@Composable
fun ServiceBox(title: String, imageResId: Int, navController: NavHostController, route: String) {
    var isClicked by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isClicked) Color.Gray else Color.LightGray
    )

    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable {
                isClicked = !isClicked
                navController.navigate("search?keyword=$title")
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "$title Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
        )
    }
}


@Preview(showBackground = true, name = "Home Fragment Preview")
@Composable
fun HomeFragmentPreview() {
    MaterialTheme {
        HomeFragment(navController = rememberNavController())
    }
}
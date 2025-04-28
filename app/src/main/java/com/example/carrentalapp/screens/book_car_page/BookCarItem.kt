package com.example.carrentalapp.screens.book_car_page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carrentalapp.data.Booking
import com.example.carrentalapp.data.Car

@Composable
fun BookCarItem(
    car: Car,
){


    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(Color(0xffd6d6c1)),
        modifier = Modifier
            .clickable(enabled = false, onClick = {})
            .size(width = 430.dp, height = 230.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Car brand: ${car.brand}",
                    fontSize = 24.sp,
                    color = Color(0xff3e3d32)
                )
                Text(
                    text = "Car model: ${car.model}",
                    fontSize = 24.sp,
                    color = Color(0xff3e3d32)
                )
                Text(
                    text = "License plate: ${car.licensePlate}",
                    fontSize = 24.sp,
                    color = Color(0xff3e3d32)
                )
                Text(
                    text = "Price per day: ${car.pricePerDayUsd.toString()}",
                    fontSize = 24.sp,
                )
            }
        }
    }
}
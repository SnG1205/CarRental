package com.example.carrentalapp.screens.user_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carrentalapp.data.Car

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarItem(
    car: Car,
    event: () -> Unit
) {
    ElevatedCard(
        onClick = event,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(Color(0xffd9c5ac)),
        modifier = Modifier
            .size(width = 430.dp, height = 125.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier,
                    Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = car.brand,
                        fontSize = 20.sp,
                        color = Color(0xff3e3d32)
                    )
                    Text(
                        text = car.model,
                        fontSize = 20.sp,
                        color = Color(0xff3e3d32)
                    )
                }
                Row(
                    modifier = Modifier,
                    Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "License plate: ${car.licensePlate}",
                        fontSize = 18.sp,
                        color = Color(0xff3e3d32)
                    )

                }
                Text(
                    text = "Year: ${car.manufactureYear.toString()}",
                    fontSize = 18.sp,
                    color = Color(0xff3e3d32)
                )
            }
            Column(
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .fillMaxSize()
                    .align(Alignment.Bottom)
            ) {
                Spacer(modifier = Modifier.height(67.dp))
                Text(
                    text = "Price per day: ${car.pricePerDayUsd.toString()}",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Right,
                    modifier = Modifier
                        .align(Alignment.End)
                )
            }
            /*Text(
                text = "Price per day: ${car.pricePerDayUsd.toString()}",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Bottom),
                textAlign = TextAlign.Right
            )*/
        }
    }
}
package com.example.carrentalapp.screens.bookings_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carrentalapp.data.Booking
import kotlin.math.exp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingItem(
    booking: Booking,
    event: () -> Unit
){
    var expanded by remember {
        mutableStateOf(false)
    }

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {expanded = !expanded},
        colors = CardDefaults.cardColors(Color(0xffd6d6c1)),
        modifier = Modifier
            .size(width = 430.dp, height = 100.dp)
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
                        text = booking.car.brand,
                        fontSize = 20.sp,
                        color = Color(0xff34a3d9)
                    )
                    Text(
                        text = booking.car.model,
                        fontSize = 20.sp,
                        color = Color(0xff3c4a52)
                    )
                }
                Row(
                    modifier = Modifier,
                    Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = booking.car.licensePlate,
                        fontSize = 20.sp,
                        color = Color(0xff333333)
                    )
                    Text(
                        text = booking.endDate.toString(),
                        fontSize = 20.sp,
                        color = Color(0xff3e3d32)
                    )
                }
            }
            Text(
                text = booking.totalCostUsd.toString(),
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Right
            )
            DropdownMenu(
                expanded = expanded,
                /*modifier = Modifier
                    .align(Alignment.TopEnd),*/
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    onClick = event
                )
                {
                    androidx.compose.material.Text("Return this car")
                }
            }
        }
    }
}
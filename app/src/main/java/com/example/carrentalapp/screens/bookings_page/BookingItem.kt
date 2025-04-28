package com.example.carrentalapp.screens.bookings_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carrentalapp.data.Booking

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
                        text = booking.userId,
                        fontSize = 20.sp,
                        color = Color(0xff3e3d32)
                    )
                    Text(
                        text = booking.carId,
                        fontSize = 20.sp,
                        color = Color(0xff3e3d32)
                    )
                    Text(
                        text = booking.startDate,
                        fontSize = 20.sp,
                        color = Color(0xff3e3d32)
                    )
                }
                Spacer(modifier = Modifier.height(1.dp))
                Row(
                    modifier = Modifier,
                    Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "End date: ${booking.endDate.toString()}",
                        fontSize = 20.sp,
                        color = Color(0xff3e3d32)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp)
                    .fillMaxSize()
                    .align(Alignment.Bottom)
            ) {
                Spacer(modifier = Modifier.height(47.dp))
                Text(
                    text = "Total cost: ${booking.totalCostUsd.toString()}",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxSize(),
                    textAlign = TextAlign.Right
                )
            }
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
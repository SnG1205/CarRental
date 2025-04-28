package com.example.carrentalapp.screens.bookings_history_page

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carrentalapp.data.Booking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHistoryItem(
    booking: Booking,
) {


    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(Color(0xffd6d6c1)),
        modifier = Modifier
            .clickable(enabled = false, onClick = {})
            .size(width = 430.dp, height = 100.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier,
                Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = booking.userId,
                    fontSize = 17.sp,
                    color = Color(0xff3e3d32)
                )
                Text(
                    text = booking.carId,
                    fontSize = 17.sp,
                    color = Color(0xff3e3d32)
                )
                Text(
                    text = "End date: ${booking.endDate.toString()}",
                    fontSize = 17.sp,
                    color = Color(0xff3e3d32)
                )
            }
            Spacer(modifier = Modifier.height(1.dp))
            /*Row(
                modifier = Modifier,
                Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = "End date: ${booking.endDate.toString()}",
                    fontSize = 20.sp,
                    color = Color(0xff3e3d32)
                )
            }*/
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
            /*Text(
                text = "Total cost: ${booking.totalCostUsd.toString()}",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Bottom),
                textAlign = TextAlign.Right
            )*/
        }
    }
}
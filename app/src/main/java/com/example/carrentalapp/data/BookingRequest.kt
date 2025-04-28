package com.example.carrentalapp.data

import java.time.LocalDate

data class BookingRequest(
    val userId: String,
    val carId: String,
    val startDate: String,
    val endDate: String
)

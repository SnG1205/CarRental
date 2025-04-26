package com.example.carrentalapp.data

import java.time.LocalDate

data class BookingRequest(
    val userId: Long,
    val carId: Long,
    val startDate: String,
    val endDate: String
)

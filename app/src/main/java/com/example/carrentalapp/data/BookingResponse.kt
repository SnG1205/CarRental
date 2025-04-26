package com.example.carrentalapp.data

import java.time.LocalDate

data class BookingResponse(
    val bookingId: Long,
    val username: String, //Todo mb change to e-mail
    val carModel: String,
    val carBrand: String,
    val licensePlate: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val totalCostUsd: Double
)

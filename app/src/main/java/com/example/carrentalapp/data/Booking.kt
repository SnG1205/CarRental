package com.example.carrentalapp.data

import java.time.LocalDate

data class Booking(
    val id : Long?,
    val user : User,
    val car : Car,
    val startDate: String,
    val endDate: String,
    val totalCostUsd: Double,
    val active: Boolean
)

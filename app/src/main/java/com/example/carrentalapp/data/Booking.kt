package com.example.carrentalapp.data

import java.time.LocalDate

data class Booking(
    val id : Long?,
    val user : User,
    val car : Car,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val totalCostUsd: Double,
    val active: Boolean
)

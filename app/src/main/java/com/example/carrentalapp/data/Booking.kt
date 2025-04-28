package com.example.carrentalapp.data

data class Booking(
    val id : String?,
    val userId : String,
    val carId : String,
    val startDate: String,
    val endDate: String,
    val totalCostUsd: Double,
    val active: Boolean
)

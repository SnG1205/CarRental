package com.example.carrentalapp.data

data class Car(
    val id: Long?,
    val brand: String,
    val model: String,
    val manufactureYear: Int,
    val licensePlate: String,
    val pricePerDayUsd: Double,
    val available: Boolean?
)

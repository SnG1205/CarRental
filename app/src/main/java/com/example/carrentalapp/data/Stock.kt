package com.example.carrentalapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks")
data class Stock(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    //Todo add foreign key to clientId
    val clientId: Int,
    val symbols: String,
    val price: Double,
    val amount: Int
)

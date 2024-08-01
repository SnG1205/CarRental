package com.example.bachelorandroid.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class Client(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val firstName: String,
    val lastName: String,
    val address: String? = "",
    val isEmployee: Boolean
)

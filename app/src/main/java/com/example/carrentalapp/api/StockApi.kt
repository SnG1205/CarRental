package com.example.carrentalapp.api

data class StockApi(
    val ticker: String,
    val results: List<ResultsItem>
)

package com.example.bachelorandroid.api

data class StockApi(
    val ticker: String,
    val results: List<ResultsItem>
)

package com.example.carrentalapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL =
    "https://api.polygon.io"

//Todo add serialization dependency
//Todo serialization doesnt work as usual
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface StockApiService {
    @GET("v2/aggs/ticker/{symbols}/range/1/day/{formattedDate}/{formattedDate}?adjusted=true&sort=asc&limit=120&apiKey=H1KXq7xnepqsiR6kI8VXha_aBykXh2Sz") //Todo check link
    suspend fun fetchStockPrices(
        @Path("symbols") symbols: String,
        @Path ("formattedDate") formattedDate: String
    ): StockApi //Todo mb return as Flow<List<StockApi>>
}

object StockService {
    val retrofitService: StockApiService by lazy {
        retrofit.create(StockApiService::class.java)
    }
}
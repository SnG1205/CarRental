package com.example.carrentalapp.api

import com.example.carrentalapp.data.Booking
import com.example.carrentalapp.data.BookingRequest
import com.example.carrentalapp.data.Car
import com.example.carrentalapp.data.LoginRequest
import com.example.carrentalapp.data.LoginResponse
import com.example.carrentalapp.data.User
import com.example.carrentalapp.util.Endpoints
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL = "http://10.0.2.2:8081" //Todo change to AWS IP of EC2

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface CarApiService {
    //Car methods
    @GET(Endpoints.CARS_ENDPOINT)
    suspend fun getAllCars(
    ): List<Car>

    @GET("${Endpoints.CARS_ENDPOINT}/available")
    suspend fun getAvailableCars(
    ): List<Car>

    @POST(Endpoints.CARS_ENDPOINT)
    suspend fun createCar(
        @Body car: Car
    ): Car

}

object CarService {
    val retrofitService: CarApiService by lazy {
        retrofit.create(CarApiService::class.java)
    }
}
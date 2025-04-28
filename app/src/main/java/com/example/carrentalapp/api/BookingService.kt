package com.example.carrentalapp.api

import com.example.carrentalapp.data.Booking
import com.example.carrentalapp.data.BookingRequest
import com.example.carrentalapp.data.DummyBookResponse
import com.example.carrentalapp.util.Endpoints
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL = "http://10.0.2.2:8082" //Todo change to AWS IP of EC2

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface BookingApiService {

    @GET("${Endpoints.BOOKINGS_ENDPOINT}/bookings/{userId}")
    suspend fun getActiveBookingsForUser(
        @Path("userId") userId: String
    ): List<Booking>?

    @GET("${Endpoints.BOOKINGS_ENDPOINT}/history/{userId}")
    suspend fun getBookingsHistoryForUser(
        @Path("userId") userId: String,
    ): List<Booking>?

    //Booking methods
    @GET(Endpoints.BOOKINGS_ENDPOINT)
    suspend fun getAllBookings(
    ): List<Booking>

    @POST(Endpoints.BOOKINGS_ENDPOINT)
    suspend fun createBooking(
        @Body bookingRequest: BookingRequest
    ): DummyBookResponse

    @PATCH("${Endpoints.BOOKINGS_ENDPOINT}/{bookingId}/return")
    suspend fun returnBooking(
        @Path("bookingId") bookingId: String
    ): Booking?
}

object BookingService {
    val retrofitService: BookingApiService by lazy {
        retrofit.create(BookingApiService::class.java)
    }
}
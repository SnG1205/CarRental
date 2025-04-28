package com.example.carrentalapp.api

import com.example.carrentalapp.data.Booking
import com.example.carrentalapp.data.BookingRequest
import com.example.carrentalapp.data.BookingResponse
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

private const val BASE_URL = "http://13.61.33.153:8080" //Todo change to AWS IP of EC2

//Todo add serialization dependency
//Todo serialization doesnt work as usual
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface CarRentalApiService {
    //User methods
    @GET(Endpoints.USERS_ENDPOINT)
    suspend fun getAllUsers(
    ): List<User>

    @POST(Endpoints.USERS_ENDPOINT)
    suspend fun createUser(
        @Body user: User
    ): User

    @GET("${Endpoints.USERS_ENDPOINT}/{userId}/bookings")
    suspend fun getActiveBookingsForUser(
        @Header("Authorization") authHeader: String,
        @Path("userId") userId: Long
    ): List<Booking>?

    @GET("${Endpoints.USERS_ENDPOINT}/{userId}/history")
    suspend fun getBookingsHistoryForUser(
        @Header("Authorization") authHeader: String,
        @Path("userId") userId: Long,
    ): List<Booking>?

    //Car methods
    @GET(Endpoints.CARS_ENDPOINT)
    suspend fun getAllCars(
        @Header("Authorization") authHeader: String
    ): List<Car>

    @GET("${Endpoints.CARS_ENDPOINT}/available")
    suspend fun getAvailableCars(
        @Header("Authorization") authHeader: String
    ): List<Car>

    @POST(Endpoints.CARS_ENDPOINT)
    suspend fun createCar(
        @Header("Authorization") authHeader: String,
        @Body car: Car
    ): Car

    //Booking methods
    @GET(Endpoints.BOOKINGS_ENDPOINT)
    suspend fun getAllBookings(
        @Header("Authorization") authHeader: String
    ): List<Booking>

    @POST(Endpoints.BOOKINGS_ENDPOINT)
    suspend fun createBooking(
        @Header("Authorization") authHeader: String,
        @Body bookingRequest: BookingRequest
    ): Booking

    @PATCH("${Endpoints.BOOKINGS_ENDPOINT}/{bookingId}/return")
    suspend fun returnBooking(
        @Header("Authorization") authHeader: String,
        @Path("bookingId") bookingId: Long
    ): Booking

    //Auth methods
    @POST("${Endpoints.AUTHORIZATION_ENDPOINT}/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
}

object CarRentalService {
    val retrofitService: CarRentalApiService by lazy {
        retrofit.create(CarRentalApiService::class.java)
    }
}
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

private const val BASE_URL = "http://10.0.2.2:8080" //Todo change to AWS IP of EC2

//Todo add serialization dependency
//Todo serialization doesnt work as usual
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    //.addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface UserApiService {
    //User methods
    @GET(Endpoints.USERS_ENDPOINT)
    suspend fun getAllUsers(
    ): List<User>

    @POST(Endpoints.USERS_ENDPOINT)
    suspend fun createUser(
        @Body user: User
    ): User

    //Auth methods
    @POST("${Endpoints.USERS_ENDPOINT}/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse
}

object UserService {
    val retrofitService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}
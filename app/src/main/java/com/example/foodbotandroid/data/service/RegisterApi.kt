package com.example.foodbotandroid.data.service

import com.example.foodbotandroid.data.request.UserRequest
import com.example.foodbotandroid.data.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @POST("user/insert")
    suspend fun register(@Body userRequest: UserRequest): Response<RegisterResponse>
}
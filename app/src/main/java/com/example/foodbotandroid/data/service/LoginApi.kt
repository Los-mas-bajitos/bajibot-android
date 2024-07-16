package com.example.foodbotandroid.data.service

import com.example.foodbotandroid.data.request.LoginRequest
import com.example.foodbotandroid.data.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}
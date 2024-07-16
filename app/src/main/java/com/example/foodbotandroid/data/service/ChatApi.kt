package com.example.foodbotandroid.data.service

import com.example.foodbotandroid.data.request.ChatRequest
import com.example.foodbotandroid.data.response.ChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApi {
    @POST("chat")
    suspend fun chat(@Body chatRequest: ChatRequest): Response<ChatResponse>
}
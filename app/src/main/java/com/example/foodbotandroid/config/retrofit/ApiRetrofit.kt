package com.example.foodbotandroid.config.retrofit

import com.example.foodbotandroid.data.service.ChatApi
import com.example.foodbotandroid.data.service.LoginApi
import com.example.foodbotandroid.data.service.RegisterApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiRetrofit {
    private const val BASE_URL = "http://10.0.2.2:5000/"
    // private const val BASE_URL = "http://192.168.0.4:5000/"
    // private const val BASE_URL = "https://sisvita-g5-backend-1.onrender.com/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    val loginApi: LoginApi = getRetrofit().create(LoginApi::class.java)
    val registerApi: RegisterApi = getRetrofit().create(RegisterApi::class.java)
    val chatApi: ChatApi = getRetrofit().create(ChatApi::class.java)
}
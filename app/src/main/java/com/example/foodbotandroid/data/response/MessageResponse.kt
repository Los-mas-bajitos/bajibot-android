package com.example.foodbotandroid.data.response

data class MessageResponse(
    val ingredients: List<String>,
    val instructions: List<String>,
    val recipeName: String,
    val description: String,
    val message: String
)
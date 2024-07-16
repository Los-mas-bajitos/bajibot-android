package com.example.foodbotandroid.config.navigation

sealed class Screen(val route: String) {
    data object HomeScreen: Screen("home_screen")
    data object ChatScreen: Screen("chat-bot")

}
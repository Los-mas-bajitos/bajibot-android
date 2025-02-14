package com.example.foodbotandroid.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.foodbotandroid.R

@Composable
fun Background(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.yellow))
    )
}
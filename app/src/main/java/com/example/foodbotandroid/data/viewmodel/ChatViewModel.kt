package com.example.foodbotandroid.data.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodbotandroid.config.retrofit.ApiRetrofit
import com.example.foodbotandroid.data.request.ChatRequest
import com.example.foodbotandroid.data.response.ChatResponse
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val chatApi = ApiRetrofit.chatApi

    private val _listMessage = MutableLiveData<List<String>>()
    val listMessage: MutableLiveData<List<String>> get() = _listMessage

    init {
        _listMessage.value = mutableListOf()
        _listMessage.value = mutableListOf()
    }

    fun addMessageUser(message: String) {
        val currentList = _listMessage.value?.toMutableList() ?: mutableListOf()
        currentList.add(message)
        _listMessage.value = currentList
    }

    fun addMessageBot(message: String) {
        viewModelScope.launch {
            try {
                val chatRequest = ChatRequest(message)
                Log.i("ChatViewModel", "Request: $chatRequest")
                val response = chatApi.chat(chatRequest)
                val currentList = _listMessage.value?.toMutableList() ?: mutableListOf()
                response.body()?.let { formatResponse(it) }?.let { currentList.add(it) }
                _listMessage.value = currentList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun formatResponse(response: ChatResponse): String {
        return if (response.response.ingredients != null){
            formatRecipeResponse(response)
        } else if (response.response.description != null){
            formatRecommendationResponse(response)
        } else {
            formatCommonResponse(response)
        }
    }

    private fun formatCommonResponse(response: ChatResponse): String{
        val message = response.response
        return message.message
    }

    private fun formatRecommendationResponse(response: ChatResponse): String{
        val message = response.response
        val stringBuilder = StringBuilder()
        stringBuilder.append("Nombre: ")
        stringBuilder.append(message.recipeName)
        stringBuilder.append("\n")
        stringBuilder.append("Descripcion: ")
        stringBuilder.append(message.description)
        return stringBuilder.toString()
    }

    private fun formatRecipeResponse(response: ChatResponse): String{
        val message = response.response
        val stringBuilder = StringBuilder()
        stringBuilder.append("Nombre: ")
        stringBuilder.append(message.recipeName)
        stringBuilder.append("\n")
        stringBuilder.append("Ingredientes: ")
        stringBuilder.append("\n")
        for (ingredient in message.ingredients) {
            stringBuilder.append(ingredient)
            stringBuilder.append("\n")
        }
        stringBuilder.append("\n")
        stringBuilder.append("Instrucciones: ")
        stringBuilder.append("\n")
        for (instruction in message.instructions) {
            stringBuilder.append(instruction)
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }
}
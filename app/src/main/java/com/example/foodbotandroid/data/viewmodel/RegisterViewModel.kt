package com.example.foodbotandroid.data.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodbotandroid.R
import com.example.foodbotandroid.config.navigation.ContextAplication
import com.example.foodbotandroid.data.request.UserRequest
import com.example.foodbotandroid.config.retrofit.ApiRetrofit
import kotlinx.coroutines.launch
import org.json.JSONObject


class RegisterViewModel : ViewModel() {

    private val registerApi = ApiRetrofit.registerApi

    private val _user = MutableLiveData("")
    val user: LiveData<String> = _user

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _title = MutableLiveData("")
    val title: LiveData<String> = _title

    private val _message = MutableLiveData("")
    val message: LiveData<String> = _message

    fun onUserChanged(newEmail: String) {
        _user.value = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun clearData() {
        _user.value = ""
        _password.value = ""
    }

    fun onRegisterClicked() {
        val context: Context = ContextAplication.applicationContext()
        viewModelScope.launch {
            try {
                if (allIsNotNullOrEmpty()) {
                    _title.value = context.getString(R.string.error)
                    _message.value = context.getString(R.string.please_fill_all_fields)
                    return@launch
                }

                val registerRequest = buildPatientRequest()

                _title.value = context.getString(R.string.register_in_progress)
                _message.value = context.getString(R.string.loading)

                val response = registerApi.register(registerRequest)
                if (response.isSuccessful) {
                    response.body()?.let {
                        val registerResponse = it
                        _title.value = context.getString(R.string.register_success)
                        _message.value = registerResponse.message
                    }
                } else {
                    val errorBody = JSONObject(response.errorBody()?.string()!!)
                    _title.value = context.getString(R.string.error)
                    _message.value = errorBody.getString("error")
                }
            } catch (e: Exception) {
                _title.value = context.getString(R.string.error)
                _message.value = e.message.toString()
            }
        }
    }

    private fun allIsNotNullOrEmpty(): Boolean {
        return user.value.isNullOrEmpty() || password.value.isNullOrEmpty()
    }

    private fun buildPatientRequest(): UserRequest {
        val userRequest = UserRequest(
            password = password.value!!,
            username = user.value!!
        )
        return userRequest
    }
}
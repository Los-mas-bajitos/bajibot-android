package com.example.foodbotandroid.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodbotandroid.R
import com.example.foodbotandroid.config.navigation.ContextAplication
import com.example.foodbotandroid.data.viewmodel.RegisterViewModel
import com.example.foodbotandroid.ui.components.CustomDialog
import com.example.foodbotandroid.ui.components.CustomEditTextField
import com.example.foodbotandroid.ui.components.CustomFooter

@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel = viewModel(modelClass = RegisterViewModel::class.java),
    showScreen: MutableIntState
) {
    Formulary(
        registerViewModel = registerViewModel
    )
    Spacer(modifier = Modifier.height(15.dp))
    CustomFooter(
        showScreen = showScreen,
        value = R.string.login,
        blackText = R.string.already_account,
        redText = R.string.login
    )
    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
private fun Formulary(
    registerViewModel: RegisterViewModel
) {
    val title by registerViewModel.title.observeAsState("")
    val message by registerViewModel.message.observeAsState("")
    val user by registerViewModel.user.observeAsState("")
    val password by registerViewModel.password.observeAsState("")
    CustomEditTextField(
        label = R.string.username, keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
        ), value = user, onValueChanged = { registerViewModel.onUserChanged(it) }, type = 0
    )
    Spacer(modifier = Modifier.padding(bottom = 15.dp))
    CustomEditTextField(
        label = R.string.password, keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
        ), value = password, onValueChanged = { registerViewModel.onPasswordChanged(it) }, type = 1
    )
    Spacer(modifier = Modifier.padding(bottom = 15.dp))
    CustomDialog(
        buttonText = R.string.register_button,
        title = title,
        content = message,
        onClick = { registerViewModel.onRegisterClicked() },
        dialogOnClick = {
            if (title == ContextAplication.applicationContext()
                    .getString(R.string.register_success)
            ) {
                registerViewModel.clearData()
            }
        }
    )
}
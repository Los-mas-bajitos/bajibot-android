package com.example.foodbotandroid.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodbotandroid.R
import com.example.foodbotandroid.data.viewmodel.ChatViewModel
import com.example.foodbotandroid.ui.components.Background
import com.example.foodbotandroid.ui.components.CustomHeader

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel = viewModel(modelClass = ChatViewModel::class.java)
) {
    var text by remember { mutableStateOf("") }
    val messages = chatViewModel.listMessage.observeAsState(emptyList())
    Background()
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        CustomHeader(DpSize(100.dp, 100.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(10.dp)
                .height(620.dp)
                .background(
                    colorResource(id = R.color.purple), shape = RoundedCornerShape(12.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                LazyColumn (
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(8.dp)
                        .height(500.dp),
                    verticalArrangement = Arrangement.Bottom,
                ){
                    items(messages.value.size) { index ->
                        Item(
                            text = messages.value[index],
                            isUser = index % 2 == 0
                        )
                    }
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    ExpandingTextField (
                        value = text,
                        onValueChange = { text = it },
                    )
                    IconButton(onClick = {
                        chatViewModel.addMessageUser(text)
                        chatViewModel.addMessageBot(text)
                        text = ""
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExpandingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false
) {
    var textFieldHeight by remember { mutableIntStateOf(0) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(8.dp)
            .background(Color.White, shape = RoundedCornerShape(4.dp))
            .onSizeChanged { textFieldHeight = it.height }
            .heightIn(min = 56.dp, max = 200.dp),
        readOnly = readOnly
    )
}

@Composable
fun Item(
    text: String,
    isUser : Boolean = true
){
    Row (
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(8.dp),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        if (isUser){
            ExpandingTextField(
                value = text,
                onValueChange = {},
                readOnly = true
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send"
                )
            }
        }
        else{
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send"
                )
            }
            ExpandingTextField(
                value = text,
                onValueChange = {},
                readOnly = true
            )
        }
    }
}
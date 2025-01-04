package com.furkanharmanc.aventra.ui.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(modifier: Modifier = Modifier, navController: NavController) {
    val viewModel: ChatViewModel = viewModel()
    val viewState by viewModel.viewState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Aventra AI", style = MaterialTheme.typography.headlineMedium)
                },
                navigationIcon = {
                    IconButton(onClick = navController::navigateUp) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Get Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            if (viewState.loading) LinearProgressIndicator(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally))

            LazyColumn(modifier = Modifier.weight(1f), reverseLayout = true) {
                items(viewState.messages) {message ->
                    MessageBubble(message = message)
                }
            }
            
            MessageInput(
                prompt = viewState.prompt,
                onPromptChange = viewModel::updatePrompt,
                onMessageSend = viewModel::makePrompt
            )
        }
    }
}

@Composable
fun MessageBubble(modifier: Modifier = Modifier, message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (message.isAI) Arrangement.Start else Arrangement.End
    ) {
        Box(
            modifier = modifier
                .background(
                    color = if (message.isAI) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(12.dp)
        ) {
            Text(text = message.text, color = if (message.isAI) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun MessageInput(
    prompt: String,
    onPromptChange: (String) -> Unit,
    onMessageSend: (String) -> Unit
) {
    var inputText by remember { mutableStateOf(prompt) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = inputText,
            onValueChange = {
                inputText = it
                onPromptChange(it)
            },
            modifier = Modifier.weight(1f),
            placeholder = { Text("Yeni yerler keşfet...") }
        )
        IconButton(
            onClick = {
                if (inputText.isNotBlank()) {
                    onMessageSend(inputText)
                    inputText = ""
                }
            }
        ) {
            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
        }
    }
}

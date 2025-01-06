package com.furkanharmanc.aventra.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkanharmanc.aventra.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {

    private val _viewState = MutableStateFlow(ChatViewState())
    val viewState = _viewState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.API_KEY
    )

    fun makePrompt(prompt: String) {
        val userMessage = Message(prompt, isAI = false)
        _viewState.update {
            it.copy(
                messages = it.messages + userMessage,
                loading = true
            )
        }
        viewModelScope.launch {
            try {
                val promptResponse = generativeModel.generateContent(prompt)
                val aiMessage = Message(promptResponse.text.toString(), isAI = true)

                _viewState.update {
                    it.copy(
                        messages = it.messages + aiMessage,
                        loading = false
                    )
                }
            } catch (e: Exception) {
                val errorMessage = Message("Bir hata oluştu: ${e.message}", isAI = true)
                _viewState.update {
                    it.copy(
                        messages = it.messages + errorMessage,
                        loading = false
                    )
                }
            }
        }
    }

    fun updatePrompt(prompt: String) {
        _viewState.update { it.copy(prompt = prompt) }
    }
}

data class ChatViewState(
    val prompt: String = "",
    val loading: Boolean = false,
    val messages: List<Message> = emptyList()
)

data class Message(
    val text: String,
    val isAI: Boolean,
)
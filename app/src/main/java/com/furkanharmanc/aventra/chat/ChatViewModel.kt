package com.furkanharmanc.aventra.chat

import androidx.lifecycle.ViewModel
import com.furkanharmanc.aventra.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel

class ChatViewModel: ViewModel() {
    val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.API_KEY
    )
}
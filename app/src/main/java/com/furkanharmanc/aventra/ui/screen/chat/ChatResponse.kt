package com.furkanharmanc.aventra.ui.screen.chat

sealed class ChatResponse {
    data object Welcome: ChatResponse()
    data object Error: ChatResponse()
    data class Success(val result: String): ChatResponse()
}
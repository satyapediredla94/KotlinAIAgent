package com.example.kotlinaiagent.data.model

import ai.koog.agents.core.tools.annotations.Tool
import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val model: String,
    val messages: List<Message>,
    val stream: Boolean = false
)

@Tool
fun sendMessage(request: ChatRequest): ChatResponse? {
    return null
}

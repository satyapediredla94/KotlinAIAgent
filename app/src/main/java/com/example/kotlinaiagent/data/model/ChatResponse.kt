package com.example.kotlinaiagent.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val message: Message
)
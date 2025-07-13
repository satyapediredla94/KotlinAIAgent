package com.example.kotlinaiagent.data.network

import com.example.kotlinaiagent.data.model.ChatRequest
import com.example.kotlinaiagent.data.model.ChatResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OllamaApi {
    @POST("/api/chat")
    fun sendMessage(@Body request: ChatRequest): Call<ChatResponse>
}
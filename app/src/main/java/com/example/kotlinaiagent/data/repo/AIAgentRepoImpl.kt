package com.example.kotlinaiagent.data.repo

import com.example.kotlinaiagent.common.Resource
import com.example.kotlinaiagent.data.AssistantType
import com.example.kotlinaiagent.data.KoogAgent
import com.example.kotlinaiagent.data.model.ChatRequest
import com.example.kotlinaiagent.data.model.Message
import com.example.kotlinaiagent.data.network.RetrofitInstance
import com.example.kotlinaiagent.domain.AIAgentRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.await

class AIAgentRepoImpl(
    private val koogAgent: KoogAgent = KoogAgent()
) : AIAgentRepo {
    override suspend fun queryUsingKoog(
        query: String,
        agentType: AssistantType
    ) = flow {
        emit(Resource.Loading)
        koogAgent.initializeKoogAgent(agentType)
            .onSuccess { agent ->
                val response = agent.runAndGetResult(query)
                emit(Resource.Success(response))
            }
            .onFailure {
                emit(Resource.Error(it.message.toString()))
            }
    }

    override suspend fun queryUsingRetrofit(query: String): Flow<Resource> = flow {
        val systemMessage = Message(
            role = "system",
            """
                You are a helpful Android development assistant. 
                Provide clear, concise, and accurate answers related to Android development, 
                Kotlin, and Jetpack Compose.
            """.trimIndent()
        )
        val userMessage = Message("user", query)
        val request = ChatRequest(
            model = "llama3.2",
            messages = listOf(systemMessage, userMessage)
        )
        val response = RetrofitInstance.api.sendMessage(request).await()
        emit(Resource.Success(response.message.content))
    }

}
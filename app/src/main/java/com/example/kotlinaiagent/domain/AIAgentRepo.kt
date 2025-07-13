package com.example.kotlinaiagent.domain

import com.example.kotlinaiagent.common.Resource
import com.example.kotlinaiagent.data.AssistantType
import kotlinx.coroutines.flow.Flow

interface AIAgentRepo {
    suspend fun queryUsingKoog(
        query: String,
        agentType: AssistantType = AssistantType.ANDROID
    ): Flow<Resource>

    suspend fun queryUsingRetrofit(query: String): Flow<Resource>
}
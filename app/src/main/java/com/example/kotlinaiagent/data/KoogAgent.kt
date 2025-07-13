package com.example.kotlinaiagent.data

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.llms.all.simpleOllamaAIExecutor
import ai.koog.prompt.llm.OllamaModels

// Initialize Koog AI Agent
class KoogAgent {
    private lateinit var aiAgent: AIAgent

    fun initializeKoogAgent(agentType: AssistantType): Result<AIAgent> {
        aiAgent = AIAgent(
            executor = simpleOllamaAIExecutor(baseUrl = "http://10.0.2.2:11434"),
            systemPrompt = getAgent(agentType),
            llmModel = OllamaModels.Meta.LLAMA_3_2
        )
        return Result.success(aiAgent)
    }

    private fun getAgent(agentType: AssistantType): String {
        return when (agentType) {
            AssistantType.ANDROID -> """
                    You are a helpful Android development assistant. 
                    Provide clear, concise, and accurate answers related 
                    to Android development, Kotlin, and Jetpack Compose.
                """.trimIndent()

            AssistantType.TRAVEL -> """
                    You are a helpful Travel assistant. 
                    Provide clear, concise, and accurate answers related 
                    to planning and organizing trips.
                """.trimIndent()

            AssistantType.PHOTOGRAPHY -> """
                    You are a helpful Photography assistant. 
                    Provide clear, concise, and accurate answers related 
                    to photography techniques and tips.
                """.trimIndent()
        }
    }
}
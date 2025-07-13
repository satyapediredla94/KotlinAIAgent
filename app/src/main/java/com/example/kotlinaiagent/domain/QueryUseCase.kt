package com.example.kotlinaiagent.domain

import com.example.kotlinaiagent.common.QueryAndroidUseCase
import com.example.kotlinaiagent.common.Resource
import com.example.kotlinaiagent.common.Resource.Error
import com.example.kotlinaiagent.common.Resource.Loading
import com.example.kotlinaiagent.common.Resource.Success
import com.example.kotlinaiagent.data.repo.AIAgentRepoImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class QueryUseCase(
    private val aiAgentRepo: AIAgentRepo = AIAgentRepoImpl()
) : QueryAndroidUseCase<String> {
    override suspend fun execute(query: String): Flow<Resource> = flow {
        try {
            aiAgentRepo.queryUsingKoog(query).collect { result ->
                when (result) {
                    is Success<*> -> {
                        if (result.data is String) {
                            emit(Success(result.data))
                        } else {
                            emit(Error("Unexpected data type in Success"))
                        }
                    }

                    is Error, is Loading -> emit(result)
                }
            }
        } catch (e: Exception) {
            emit(Error("Exception during query execution: ${e.message}"))
        }
    }
}
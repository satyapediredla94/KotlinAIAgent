package com.example.kotlinaiagent.common

import kotlinx.coroutines.flow.Flow

interface QueryAndroidUseCase<T> {
    suspend fun execute(query: T) : Flow<Resource>
}
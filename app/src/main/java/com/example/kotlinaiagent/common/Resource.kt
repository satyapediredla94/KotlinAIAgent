package com.example.kotlinaiagent.common

sealed interface Resource {
    data class Success<T>(val data: T) : Resource
    data class Error(val message: String) : Resource
    data object Loading : Resource
}
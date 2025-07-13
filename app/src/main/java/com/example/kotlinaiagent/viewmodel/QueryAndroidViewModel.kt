package com.example.kotlinaiagent.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinaiagent.common.Resource
import com.example.kotlinaiagent.domain.QueryUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class QueryAndroidViewModel : ViewModel() {

    private val _state = mutableStateOf(QueryState())
    val queryState: State<QueryState> = _state

    var job: Job? = null

    data class QueryState(
        val isLoading: Boolean = false,
        val data: String? = null,
        val error: String? = null
    )

    init {
        query("Hi")
    }

    private val queryAndroidUseCase = QueryUseCase()

    fun query(query: String) {
        _state.value = _state.value.copy(isLoading = true)
        job?.cancel()
        if (query.isBlank()) return
        job = viewModelScope.launch {
            delay(2.seconds)
            performQuery(query)
        }
    }

    private suspend fun performQuery(query: String) {
        queryAndroidUseCase.execute(query).collectLatest { result ->
            when (result) {
                is Resource.Success<*> -> {
                    // Handle success
                    val data = result.data
                    if (data is String) {
                        _state.value = _state.value.copy(data = data, isLoading = false)
                    } else {
                        _state.value = _state.value.copy(
                            error = "Unexpected data type in Success",
                            isLoading = false
                        )
                    }
                }

                is Resource.Error -> {
                    // Handle error
                    _state.value = _state.value.copy(
                        error = result.message,
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    // Handle loading
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }
}
package com.test2.repositories

sealed interface TestResponse<T> {
    data class Success<T>(val data: T) : TestResponse<T>
    data class Error<T>(val error: Exception) : TestResponse<T>
}

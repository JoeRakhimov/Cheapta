package com.cheapta.app.data

sealed class Result<out T> {
    data class Error(val exception: Exception) : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
}
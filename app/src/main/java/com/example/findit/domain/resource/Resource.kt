package com.example.findit.domain.resource

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val errorMessage: String) : Resource<Nothing>()
    data class Loader(val isLoading : Boolean) : Resource<Nothing>()
}
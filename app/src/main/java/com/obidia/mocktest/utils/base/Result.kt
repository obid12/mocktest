package com.obidia.mocktest.utils.base

sealed class Resource<out T : Any, out U : Any> {
    data class Success<T : Any>(val data: T) : Resource<T, Nothing>()
    data class Error<U : Any>(val response: U) : Resource<Nothing, U>()
}
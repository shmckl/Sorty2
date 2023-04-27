package com.example.sorty2.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Resource<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(error: String, data: T? = null) : Resource<T>(data = data, error = error)
}
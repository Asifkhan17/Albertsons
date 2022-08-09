package com.demo.albertsons.util

sealed class Resource(
    val data: Any? = null,
    val message: String? = null
) {
    class Success(data: Any): Resource(data)
    class Error(message: String, data: Any? = null): Resource(data, message)
    class Loading : Resource()
}

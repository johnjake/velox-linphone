package com.velox.data.response

data class ErrorResponse(
    val message: String? = null,
    val exception: Throwable? = null
)

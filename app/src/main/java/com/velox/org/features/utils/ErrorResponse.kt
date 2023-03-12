package com.velox.org.features.utils

data class ErrorResponse(
    val message: String? = null,
    val exception: Throwable? = null
)

package com.velox.data.response

open class CancellationException(
    private val errorResponse: ErrorResponse
) : RuntimeException(errorResponse.message)

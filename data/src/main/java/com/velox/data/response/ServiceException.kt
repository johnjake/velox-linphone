package com.velox.data.response

open class ServiceException(
    private val errorResponse: ErrorResponse,
) : RuntimeException(errorResponse.message)

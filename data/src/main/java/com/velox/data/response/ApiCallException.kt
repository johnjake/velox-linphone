package com.velox.data.response // ktlint-disable filename

import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketException
import java.net.UnknownHostException

@Throws(ServiceException::class)
inline fun <reified T> apiSafeCall(call: () -> T) = try {
    call()
} catch (e: CancellationException) {
    Timber.e(e)

    throw CancellationException(
        ErrorResponse(
            message = e.message,
            exception = e.cause,
        ),
    )
} catch (e: Exception) {
    /**
     * We can add other exception here and also if we want to check the
     * status we can also add here.
     **/

    Timber.e(e)

    throw ServiceException(
        when (e) {
            is UnknownHostException -> {
                ErrorResponse(
                    message = "No internet connection!",
                    exception = e.cause,
                )
            }
            is SocketException -> {
                ErrorResponse(
                    message = e.message,
                    exception = e.cause,
                )
            }
            is CancellationException -> {
                ErrorResponse(
                    message = e.message,
                    exception = e.cause,
                )
            }
            is HttpException -> {
                /** val errorMessage = e.response()?.errorBody().extractErrorDetail() ?: e.message
                 val errorMessagePaymaya = e.response()?.errorBody().extractPaymayaErrorDetail() ?: e.message
                 val errMessage = when (errorMessage) {
                 ERROR_PARSING_UNSUCCESSFUL_PAY_MAYA_ERROR_DETAIL -> {
                 errorMessagePaymaya
                 }
                 else -> {
                 errorMessage
                 }
                 }**/
                /** val errorMessage = e.response()?.errorBody().extractErrorDetail() ?: e.message
                 val errorMessagePaymaya = e.response()?.errorBody().extractPaymayaErrorDetail() ?: e.message
                 val errMessage = when (errorMessage) {
                 ERROR_PARSING_UNSUCCESSFUL_PAY_MAYA_ERROR_DETAIL -> {
                 errorMessagePaymaya
                 }
                 else -> {
                 errorMessage
                 }
                 }**/
                /** val errorMessage = e.response()?.errorBody().extractErrorDetail() ?: e.message
                 val errorMessagePaymaya = e.response()?.errorBody().extractPaymayaErrorDetail() ?: e.message
                 val errMessage = when (errorMessage) {
                 ERROR_PARSING_UNSUCCESSFUL_PAY_MAYA_ERROR_DETAIL -> {
                 errorMessagePaymaya
                 }
                 else -> {
                 errorMessage
                 }
                 }**/
                /** val errorMessage = e.response()?.errorBody().extractErrorDetail() ?: e.message
                 val errorMessagePaymaya = e.response()?.errorBody().extractPaymayaErrorDetail() ?: e.message
                 val errMessage = when (errorMessage) {
                 ERROR_PARSING_UNSUCCESSFUL_PAY_MAYA_ERROR_DETAIL -> {
                 errorMessagePaymaya
                 }
                 else -> {
                 errorMessage
                 }
                 }**/
                /** val errorMessage = e.response()?.errorBody().extractErrorDetail() ?: e.message
                 val errorMessagePaymaya = e.response()?.errorBody().extractPaymayaErrorDetail() ?: e.message
                 val errMessage = when (errorMessage) {
                 ERROR_PARSING_UNSUCCESSFUL_PAY_MAYA_ERROR_DETAIL -> {
                 errorMessagePaymaya
                 }
                 else -> {
                 errorMessage
                 }
                 }**/
                /** val errorMessage = e.response()?.errorBody().extractErrorDetail() ?: e.message
                 val errorMessagePaymaya = e.response()?.errorBody().extractPaymayaErrorDetail() ?: e.message
                 val errMessage = when (errorMessage) {
                 ERROR_PARSING_UNSUCCESSFUL_PAY_MAYA_ERROR_DETAIL -> {
                 errorMessagePaymaya
                 }
                 else -> {
                 errorMessage
                 }
                 }**/
                /** val errorMessage = e.response()?.errorBody().extractErrorDetail() ?: e.message
                 val errorMessagePaymaya = e.response()?.errorBody().extractPaymayaErrorDetail() ?: e.message
                 val errMessage = when (errorMessage) {
                 ERROR_PARSING_UNSUCCESSFUL_PAY_MAYA_ERROR_DETAIL -> {
                 errorMessagePaymaya
                 }
                 else -> {
                 errorMessage
                 }
                 }**/
                /** val errorMessage = e.response()?.errorBody().extractErrorDetail() ?: e.message
                 val errorMessagePaymaya = e.response()?.errorBody().extractPaymayaErrorDetail() ?: e.message
                 val errMessage = when (errorMessage) {
                 ERROR_PARSING_UNSUCCESSFUL_PAY_MAYA_ERROR_DETAIL -> {
                 errorMessagePaymaya
                 }
                 else -> {
                 errorMessage
                 }
                 }**/
                ErrorResponse(
                    message = e.message,
                    exception = e.cause,
                )
            }
            else -> {
                ErrorResponse(
                    message = e.message,
                    exception = e.cause,
                )
            }
        },
    )
}

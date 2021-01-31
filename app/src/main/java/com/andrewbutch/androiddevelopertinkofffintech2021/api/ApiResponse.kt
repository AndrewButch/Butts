package com.andrewbutch.androiddevelopertinkofffintech2021.api

import com.andrewbutch.androiddevelopertinkofffintech2021.utils.Constants.NETWORK_ERROR_UNKNOWN
import retrofit2.Response

// Code from Google samples
// https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/api/ApiResponse.kt

sealed class ApiResponse<T> {

    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: NETWORK_ERROR_UNKNOWN)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        body = body,
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMsg ?: NETWORK_ERROR_UNKNOWN)
            }
        }
    }

}

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()
data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()

// Represent success response with HTTP 204 code (empty body)
class ApiEmptyResponse<T> : ApiResponse<T>()
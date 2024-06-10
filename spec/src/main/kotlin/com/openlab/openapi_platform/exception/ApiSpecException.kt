package com.openlab.openapi_platform.exception

import com.fasterxml.jackson.annotation.JsonProperty

class ApiSpecException(errorCode: ApiSpecErrorCode, message: String) : Exception(message) {
    fun logError() {
        println("Error Code: , Message: $message")
    }
}

enum class ApiSpecErrorCode(val code: kotlin.String, val value: kotlin.String) {

    INVALID_SPEC("SPEC001", "Invalid spec"),
    NOTFOUND_SPEC("SPEC002", "Not found spec");

    fun getCode(): kotlin.String {
        return value.toString()
    }
}
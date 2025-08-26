package com.study.kotlog.exception

data class FrontException(
    val errorMessage: FrontErrorCode,
) : RuntimeException()

enum class FrontErrorCode {
    INVALID_TOKEN,
    EXPIRED_TOKEN,
}

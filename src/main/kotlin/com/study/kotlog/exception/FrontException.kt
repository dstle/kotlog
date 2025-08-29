package com.study.kotlog.exception

data class FrontException(
    val errorMessage: FrontErrorCode,
) : RuntimeException()

enum class FrontErrorCode {
    INVALID_TOKEN,
    EXPIRED_TOKEN,
    REFRESH_TOKEN_NOT_FOUND,
    REFRESH_TOKEN_NOT_MATCH,
}

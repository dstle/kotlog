package com.study.kotlog.exception

import java.lang.RuntimeException

data class FrontException(
    val errorCode: FrontErrorCode,
) : RuntimeException()

enum class FrontErrorCode {
    INVALID_JWT,
    EXPIRED_JWT,
}

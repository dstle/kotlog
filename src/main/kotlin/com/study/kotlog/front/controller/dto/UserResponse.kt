package com.study.kotlog.front.controller.dto

import com.study.kotlog.domain.user.User

data class UserResponse(
    val username: String,
    val email: String,
    val nickname: String,
) {
    companion object {
        fun from(user: User): UserResponse = UserResponse(username = user.username, email = user.email, nickname = user.nickname)
    }
}

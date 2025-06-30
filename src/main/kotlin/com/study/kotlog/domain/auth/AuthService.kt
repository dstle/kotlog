package com.study.kotlog.domain.auth

import com.study.kotlog.domain.user.User
import com.study.kotlog.domain.user.UserRepository
import com.study.kotlog.front.controller.auth.dto.SignupRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    fun signUp(user: SignupRequest) {
        validateDuplicateUsername(user)
        validatePasswordPolicy(user)

        val encodedPassword = passwordEncoder.encode(user.password)

        val user =
            User(username = user.username, password = encodedPassword, email = user.email, nickname = user.nickname)

        userRepository.save(user)
    }

    private fun validateDuplicateUsername(user: SignupRequest) {
        if (userRepository.existsByUsername(user.username)) {
            throw IllegalArgumentException("Username already exists")
        }
    }

    private fun validatePasswordPolicy(user: SignupRequest) {
        if (user.password.length < 10) {
            throw IllegalArgumentException("Password must be at least 10 characters long")
        }
    }
}

package com.study.kotlog.domain.auth

import com.study.kotlog.domain.auth.dto.SignupCommand
import com.study.kotlog.domain.user.User
import com.study.kotlog.domain.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    fun signUp(signupCommand: SignupCommand) {
        validateDuplicateUsername(signupCommand)
        validatePasswordPolicy(signupCommand)

        val encodedPassword = passwordEncoder.encode(signupCommand.password)

        val user = User(
            username = signupCommand.username,
            password = encodedPassword,
            email = signupCommand.email,
            nickname = signupCommand.nickname
        )

        userRepository.save(user)
    }

    private fun validateDuplicateUsername(signupCommand: SignupCommand) {
        if (userRepository.existsByUsername(signupCommand.username)) {
            throw IllegalArgumentException("Username already exists")
        }
    }

    private fun validatePasswordPolicy(signupCommand: SignupCommand) {
        if (signupCommand.password.length < 10) {
            throw IllegalArgumentException("Password must be at least 10 characters long")
        }
    }
}

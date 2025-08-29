package com.study.kotlog.domain.auth

import com.study.kotlog.domain.auth.dto.LoginCommand
import com.study.kotlog.domain.auth.dto.LoginResult
import com.study.kotlog.domain.auth.dto.SignupCommand
import com.study.kotlog.domain.redis.RefreshTokenStore
import com.study.kotlog.domain.token.TokenService
import com.study.kotlog.domain.user.User
import com.study.kotlog.domain.user.UserRepository
import com.study.kotlog.exception.FrontErrorCode
import com.study.kotlog.exception.FrontException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService,
    private val refreshTokenStore: RefreshTokenStore,
) {
    fun signUp(signupCommand: SignupCommand) {
        validateDuplicateUsername(signupCommand.username)
        validatePasswordPolicy(signupCommand.password)

        val encodedPassword = passwordEncoder.encode(signupCommand.password)

        val user = User(
            username = signupCommand.username,
            password = encodedPassword,
            email = signupCommand.email,
            nickname = signupCommand.nickname
        )

        userRepository.save(user)
    }

    private fun validateDuplicateUsername(username: String) {
        if (userRepository.existsByUsername(username)) {
            throw IllegalArgumentException("Username already exists")
        }
    }

    private fun validatePasswordPolicy(password: String) {
        if (password.length < 10) {
            throw IllegalArgumentException("Password must be at least 10 characters long")
        }
    }

    fun login(loginCommand: LoginCommand): LoginResult {
        val user = userRepository.findByUsername(loginCommand.username)
            ?: throw IllegalArgumentException("Username not found")

        verifyPassword(loginCommand.password, user.password)

        val accessToken = tokenService.generateAccessToken(user.id)
        val refreshToken = tokenService.generateRefreshToken(user.id)

        refreshTokenStore.save(user.id, refreshToken, REDIS_REFRESH_TOKEN_EXPIRE_SECONDS)

        return LoginResult(
            accessToken = accessToken.token,
            refreshToken = refreshToken,
            expiresIn = accessToken.expiresIn
        )
    }

    private fun verifyPassword(
        loginCommandPassword: String,
        userPassword: String,
    ) {
        if (!passwordEncoder.matches(loginCommandPassword, userPassword)) {
            throw IllegalArgumentException("Passwords do not match")
        }
    }

    fun reissue(refreshToken: String): LoginResult {
        tokenService.validateRefreshToken(refreshToken)
        val userId = tokenService.extractUserId(refreshToken)

        val hash = refreshTokenStore.getHash(userId) ?: throw FrontException(FrontErrorCode.REFRESH_TOKEN_NOT_FOUND)

        if (!refreshTokenStore.matchRefreshToken(refreshToken, hash)) {
            throw FrontException(FrontErrorCode.REFRESH_TOKEN_NOT_MATCH)
        }

        val accessToken = tokenService.generateAccessToken(userId)
        val refreshToken = tokenService.generateRefreshToken(userId)

        refreshTokenStore.save(userId, refreshToken, REDIS_REFRESH_TOKEN_EXPIRE_SECONDS)

        return LoginResult(
            accessToken = accessToken.token,
            refreshToken = refreshToken,
            expiresIn = accessToken.expiresIn
        )
    }

    fun logout(userId: Long) {
        refreshTokenStore.delete(userId)
    }

    companion object {
        private const val REDIS_REFRESH_TOKEN_EXPIRE_SECONDS = 15 * 24 * 60 * 60L // 15일
    }
}

package com.study.kotlog.domain.auth.config

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class PasswordEncoderConfigTest(
    val encoder: PasswordEncoder,
) : FunSpec({
    fun generateRandomPassword(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#\$%^&*"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

    test("비밀번호 암호화 성공") {
        val rawPassword = generateRandomPassword(12)

        val encoded = encoder.encode(rawPassword)

        encoded shouldNotBe rawPassword
        encoder.matches(rawPassword, encoded) shouldBe true
        encoder.matches("wrongPassword", encoded) shouldBe false
    }
})

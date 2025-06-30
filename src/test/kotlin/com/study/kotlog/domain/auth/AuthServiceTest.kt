package com.study.kotlog.domain.auth

import com.study.kotlog.domain.user.User
import com.study.kotlog.domain.user.UserRepository
import com.study.kotlog.front.controller.auth.dto.SignupRequest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuthServiceTest(
    val authService: AuthService,
    val userRepository: UserRepository,
) : FunSpec({

    beforeTest {
        userRepository.deleteAll()
        userRepository.flush()
    }

    context("회원가입 테스트") {
        test("성공") {

            val signupRequest = SignupRequest(
                username = "dustle",
                password = "111111111111111",
                email = "111",
                nickname = "Dustle"
            )

            authService.signUp(signupRequest)

            userRepository.existsByUsername("dustle") shouldBe true
        }

        test("이미 존재하는 username 이 있어서 실패") {
            val user = User(
                username = "dustle",
                password = "111111111111111",
                email = "111",
                nickname = "Dustle"
            ).also { userRepository.save(it) }

            val signupRequest = SignupRequest(
                username = "dustle",
                password = "111111111111111",
                email = "111",
                nickname = "Dustle"
            )

            shouldThrow<IllegalArgumentException> {
                authService.signUp(signupRequest)
            }.message shouldBe "Username already exists"
        }

        test("비밀번호가 정책을 통과하지 못해서 실패") {
            val signupRequest = SignupRequest(
                username = "dustle",
                password = "1212",
                email = "111",
                nickname = "Dustle"
            )

            shouldThrow<IllegalArgumentException> {
                authService.signUp(signupRequest)
            }.message shouldBe "Password must be at least 10 characters long"
        }
    }
})

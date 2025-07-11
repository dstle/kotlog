package com.study.kotlog.domain.auth

import com.study.kotlog.domain.auth.dto.LoginCommand
import com.study.kotlog.domain.auth.dto.SignupCommand
import com.study.kotlog.domain.user.User
import com.study.kotlog.domain.user.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuthServiceTest(
    val authService: AuthService,
    val userRepository: UserRepository,
) : FunSpec({

    afterTest {
        userRepository.deleteAll()
    }

    context("회원가입 테스트") {
        test("성공") {

            val signupCommand = SignupCommand(
                username = "dustle1",
                password = "111111111111111",
                email = "111",
                nickname = "Dustle"
            )

            authService.signUp(signupCommand)

            userRepository.existsByUsername("dustle1") shouldBe true
        }

        test("이미 존재하는 username 이 있어서 실패") {
            val user = User(
                username = "dustle2",
                password = "111111111111111",
                email = "111",
                nickname = "Dustle"
            ).also { userRepository.save(it) }

            val signupCommand = SignupCommand(
                username = "dustle2",
                password = "111111111111111",
                email = "111",
                nickname = "Dustle"
            )

            shouldThrow<IllegalArgumentException> {
                authService.signUp(signupCommand)
            }.message shouldBe "Username already exists"
        }

        test("비밀번호가 정책을 통과하지 못해서 실패") {
            val signupCommand = SignupCommand(
                username = "dustle3",
                password = "1212",
                email = "111",
                nickname = "Dustle"
            )

            shouldThrow<IllegalArgumentException> {
                authService.signUp(signupCommand)
            }.message shouldBe "Password must be at least 10 characters long"
        }
    }

    context("로그인 테스트") {
        test("로그인 성공") {
            val signupCommand = SignupCommand(
                username = "dustle1",
                password = "111111111111111",
                email = "111",
                nickname = "Dustle"
            )

            authService.signUp(signupCommand)

            val loginCommand = LoginCommand(
                username = "dustle1",
                password = "111111111111111"
            )

            val loginResult = authService.login(loginCommand)

            loginResult.accessToken shouldNotBe null
            loginResult.refreshToken shouldNotBe null
            loginResult.expiresIn shouldBe 1800L
        }

        test("사용자가 존재하지 않아서 실패") {
            val signupCommand = SignupCommand(
                username = "dustle2",
                password = "111111111111111",
                email = "111",
                nickname = "Dustle"
            )

            authService.signUp(signupCommand)

            val loginCommand = LoginCommand(
                username = "dustle33",
                password = "111111111111111"
            )

            shouldThrow<IllegalArgumentException> {
                authService.login(loginCommand)
            }.message shouldBe "Username not found"
        }

        test("비밀번호 틀려서 실패") {
            val signupCommand = SignupCommand(
                username = "dustle3",
                password = "111111111111111",
                email = "111",
                nickname = "Dustle"
            )

            authService.signUp(signupCommand)

            val loginCommand = LoginCommand(
                username = "dustle3",
                password = "1313"
            )

            shouldThrow<IllegalArgumentException> {
                authService.login(loginCommand)
            }.message shouldBe "Passwords do not match"
        }
    }
})

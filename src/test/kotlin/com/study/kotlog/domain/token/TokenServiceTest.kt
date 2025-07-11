package com.study.kotlog.domain.token

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import kotlin.math.absoluteValue
import kotlin.random.Random

@SpringBootTest
class TokenServiceTest(
    val tokenService: TokenService,
) : FunSpec(
    {
        test("accessToken DTO 만료 시간 확인") {
            val userId = Random.nextLong().absoluteValue
            val expectedExpiresIn = 30 * 60L
            val accessToken = tokenService.generateAccessToken(userId)

            accessToken.expiresIn shouldBe expectedExpiresIn
        }
    }
)

package com.study.kotlog.util

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import kotlin.math.absoluteValue
import kotlin.random.Random

@SpringBootTest
class JwtUtilTest(
    val jwtUtil: JwtUtil,
) : FunSpec({

    context("토큰 생성 및 추출") {
        test("성공") {
            val userId = Random.nextLong().absoluteValue
            val token = jwtUtil.generateToken(userId, 1000L)

            shouldNotThrow<Throwable> {
                jwtUtil.validateToken(token)
            }
            jwtUtil.extractUserId(token) shouldBe userId
        }

        test("validate token 실패 : 잘못된 token") {
            shouldThrowExactly<MalformedJwtException> {
                jwtUtil.validateToken("invalid")
            }
        }

        test("validate token 실패 : 기간 만료") {
            val userId = Random.nextLong().absoluteValue
            val token = jwtUtil.generateToken(userId, -1000L)

            shouldThrowExactly<ExpiredJwtException> {
                jwtUtil.validateToken(token)
            }
        }

        test("만료 시간 확인") {
            val userId = Random.nextLong().absoluteValue
            val token = jwtUtil.generateToken(userId, 1000L)

            val extractExpiration = jwtUtil.extractExpiration(token)
            val extractIssuedAt = jwtUtil.extractIssuedAt(token)

            val diff = extractExpiration - extractIssuedAt
            diff shouldBe 1000L
        }

        test("extract token 비교") {
            val userId1 = Random.nextLong().absoluteValue
            val token1 = jwtUtil.generateToken(userId1, 1000L)
            shouldNotThrow<Throwable> {
                jwtUtil.validateToken(token1)
            }
            val extractUserId1 = jwtUtil.extractUserId(token1)

            val userId2 = Random.nextLong().absoluteValue
            val token2 = jwtUtil.generateToken(userId2, 1000L)
            shouldNotThrow<Throwable> {
                jwtUtil.validateToken(token2)
            }
            val extractUserId2 = jwtUtil.extractUserId(token2)

            extractUserId1 shouldBe userId1
            extractUserId2 shouldBe userId2
            extractUserId1 shouldNotBe extractUserId2
        }
    }
})

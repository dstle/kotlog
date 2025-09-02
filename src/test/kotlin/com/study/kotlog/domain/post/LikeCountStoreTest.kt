package com.study.kotlog.domain.post

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.StringRedisTemplate

@SpringBootTest
class LikeCountStoreTest(
    val likeCountStore: LikeCountStore,
    val redis: StringRedisTemplate,
) : FunSpec({
        beforeTest {
            redis.connectionFactory?.connection?.serverCommands()?.flushAll()
        }

        test("Like 증가 성공") {
            val increment = likeCountStore.increment(1)

            increment shouldBe 1
        }

        test("Like 감소 성공") {
            likeCountStore.increment(1)
            likeCountStore.increment(1)
            likeCountStore.increment(1)
            likeCountStore.increment(1)
            val increment = likeCountStore.increment(1)

            val decrement = likeCountStore.decrement(1)

            decrement shouldBe increment - 1
        }

        test("Like 조회 성공") {
            likeCountStore.increment(1)
            likeCountStore.increment(1)
            likeCountStore.increment(1)
            val increment = likeCountStore.increment(1)

            likeCountStore.getTotal(1) shouldBe increment
        }
    }
)

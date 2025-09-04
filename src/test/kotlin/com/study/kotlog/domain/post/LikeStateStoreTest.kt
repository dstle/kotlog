package com.study.kotlog.domain.post

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.StringRedisTemplate

@SpringBootTest
class LikeStateStoreTest(
    val likeStateStore: LikeStateStore,
    val redis: StringRedisTemplate,
) : FunSpec({
    beforeTest {
        redis.connectionFactory?.connection?.serverCommands()?.flushAll()
    }

    test("사용자가 좋아요 누르기 성공") {
        val like = likeStateStore.like(1, 1)

        like shouldBe true
    }

    test("사용자가 좋아요 중복으로 눌러서 false 반환") {
        likeStateStore.like(1, 1)
        val like = likeStateStore.like(1, 1)

        like shouldBe false
    }

    test("사용자가 좋아요 취소 성공") {
        likeStateStore.like(1, 1)
        val unlike = likeStateStore.unlike(1, 1)

        unlike shouldBe true
    }

    test("사용자가 좋아요 중복으로 취소해서 false 반환") {
        val unlike = likeStateStore.unlike(1, 1)

        unlike shouldBe false
    }

    test("사용자 좋아요 누른 상태 반환 성공") {
        likeStateStore.like(1, 1)

        val liked = likeStateStore.isLiked(1, 1)

        liked shouldBe true
    }

    test("사용자 좋아요 안누른 상태 반환 성공") {
        val liked = likeStateStore.isLiked(1, 1)

        liked shouldBe false
    }
})

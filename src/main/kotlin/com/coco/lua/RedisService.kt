package com.coco.lua

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.stereotype.Service

@Service
class RedisService(
    private val redisTemplate: StringRedisTemplate,

) {
    fun get(key: String): String {
        return redisTemplate.opsForValue().get(key).toString()
    }

    fun set(key: String, value: String) {
        redisTemplate.opsForValue().set(key, value)
    }

    fun decrease(key: String, quantity: Int): Long? {
        // 음수 수량 방지 (선택 사항)
        require(quantity > 0) { "감소 수량은 0보다 커야 합니다." }

        // DECR_LUA 스크립트를 사용하여 재고 감소
        val result = redisTemplate.execute(
            DefaultRedisScript(DECR_SIMPLE_LUA, Long::class.java),
            listOf(key),
            quantity.toString()
        )
        return result
    }

    companion object {
        // Lua 스크립트: 주어진 키의 값을 ARGV[1] 만큼 감소시키고, 감소된 값을 반환합니다.
        private val DECR_SIMPLE_LUA = """
            local result = redis.call("DECRBY", KEYS[1], ARGV[1])
            return result
        """.trimIndent()
    }

}
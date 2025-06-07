package com.coco.lua

import org.springframework.data.redis.core.StringRedisTemplate
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
}
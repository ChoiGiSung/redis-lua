package com.coco.lua

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ApiController(
    private val redisService: RedisService
) {


    @GetMapping("/{key}")
    fun getValue(@PathVariable key: String): ResponseEntity<String> {
        return try {
            val value = redisService.get(key)
            ResponseEntity.ok(value ?: "값이 없습니다")
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseEntity.status(500).body("Redis Error: ${e.message}")
        }
    }

    @PostMapping
    fun setToRedis(
        @RequestBody body: Body
    ): String {
        redisService.set(body.key, body.value)
        return "success"
    }

    class Body(
        val key: String,
        val value: String
    )
}
package com.coco.lua

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConfigProperties {

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.cluster")
    fun redisProperties() = RedisProperties()

    class RedisProperties {
        var maxRedirects: Int = 0
        lateinit var connectIp: String
        lateinit var nodes: List<String>
    }

}
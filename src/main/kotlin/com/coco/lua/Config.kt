package com.coco.lua

import io.lettuce.core.ClientOptions
import io.lettuce.core.ReadFrom
import io.lettuce.core.cluster.ClusterClientOptions
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions
import io.lettuce.core.internal.HostAndPort
import io.lettuce.core.resource.ClientResources
import io.lettuce.core.resource.DnsResolvers
import io.lettuce.core.resource.MappingSocketAddressResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import java.time.Duration
import java.time.temporal.ChronoUnit


@Configuration
class Config(
    private val configProperties: ConfigProperties
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val redisClusterConfiguration = RedisClusterConfiguration(configProperties.redisProperties().nodes)
        redisClusterConfiguration.setMaxRedirects(configProperties.redisProperties().maxRedirects)
        redisClusterConfiguration.setPassword(configProperties.redisProperties().password)

        val clusterTopologyRefreshOptions: ClusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
            .enableAllAdaptiveRefreshTriggers()
            .enablePeriodicRefresh(Duration.ofHours(1L))
            .build()
        val clientOptions: ClientOptions = ClusterClientOptions.builder()
            .topologyRefreshOptions(clusterTopologyRefreshOptions)
            .build()

        val resolver = MappingSocketAddressResolver.create(
            DnsResolvers.UNRESOLVED
        ) { hostAndPort: HostAndPort ->
            val andPort =
                HostAndPort.of(configProperties.redisProperties().connectIp, hostAndPort.getPort())
            andPort
        }

        val clientResources = ClientResources.builder()
            .socketAddressResolver(resolver)
            .build()

        val clientConfiguration: LettuceClientConfiguration = LettuceClientConfiguration.builder()
            .commandTimeout(Duration.of(30, ChronoUnit.SECONDS))
            .clientOptions(clientOptions)
            .clientResources(clientResources)
            .readFrom(ReadFrom.REPLICA_PREFERRED)
            .build()


        return LettuceConnectionFactory(redisClusterConfiguration, clientConfiguration);
    }

}
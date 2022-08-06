package com.example.demo.config;

import java.time.Duration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfigurationCustom {
	
	@Bean
	public RedisCacheConfiguration redisCacheConfiguration()
	{
		return RedisCacheConfiguration.defaultCacheConfig().serializeKeysWith(RedisSerializationContext.
				SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()))
				.entryTtl(Duration.ofSeconds(600));
	}
	
	@Bean
	public RedisCacheWriter redisCacheWriter() {

        return RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory());

    }  
	
	@Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        return new LettuceConnectionFactory();
    }
	
	@Bean
    public RedisCacheManager redisCacheManager() {
        return new CustomCacheManager(redisCacheWriter(), redisCacheConfiguration());
    }

}

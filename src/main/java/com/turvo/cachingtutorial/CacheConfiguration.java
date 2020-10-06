package com.turvo.cachingtutorial;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turvo.cachingtutorial.model.Appointment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
public class CacheConfiguration {

  @Bean
  @Primary
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager();
  }


  @Bean("redisCacheManager")
  public CacheManager calendarCacheManager(
      RedisConnectionFactory connectionFactory) {

    Jackson2JsonRedisSerializer<Appointment> serializer =
        new Jackson2JsonRedisSerializer<>(Appointment.class);
    serializer.setObjectMapper(new ObjectMapper());

    RedisCacheConfiguration redisCacheConfiguration =
        RedisCacheConfiguration.defaultCacheConfig()
            .serializeValuesWith(SerializationPair.fromSerializer(serializer));

    redisCacheConfiguration.usePrefix();

    return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
        .cacheDefaults(redisCacheConfiguration)
        .build();
  }
}

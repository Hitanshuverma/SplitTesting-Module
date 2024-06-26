package com.api.splitTesting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    //dev
    @Bean
    public RedisConnectionFactory connectionFactory(){
        return new LettuceConnectionFactory();
    }

    //prod
//    @Bean
//    public RedisConnectionFactory connectionFactory() {
//        //        lettuceConnectionFactory.setDatabase(redisDatabase);
////        lettuceConnectionFactory.setPassword(redisPassword);
////        lettuceConnectionFactory.setUsername(redisUsername);
////        return new LettuceConnectionFactory(redisHost, redisPort);
//
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
//        lettuceCon
//    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate(){

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }
}

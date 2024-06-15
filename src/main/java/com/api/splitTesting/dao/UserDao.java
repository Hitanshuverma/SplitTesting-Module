package com.api.splitTesting.dao;


import com.api.splitTesting.models.User;
import com.api.splitTesting.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class UserDao {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveGuestUser(User user) {
//        redisTemplate.opsForHash().put(KEY, user.getCookieId(), user);
        redisTemplate.opsForValue().set(Constants.USER_KEY + user.getCookieId(), user);
        redisTemplate.expire(Constants.USER_KEY + user.getCookieId(), Constants.COOKIE_EXPIRATION.toSeconds(), TimeUnit.SECONDS);
    }

    public User getUser(String cookieId) {
        return (User) redisTemplate.opsForValue().get(Constants.USER_KEY + cookieId);
    }
}

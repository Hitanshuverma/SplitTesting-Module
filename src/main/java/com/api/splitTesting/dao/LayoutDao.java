package com.api.splitTesting.dao;

import com.api.splitTesting.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class LayoutDao {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Set<String> getLayouts() {
        return stringRedisTemplate.opsForSet().members(Constants.LAYOUT_KEY);
    }
}

package top.yumoyumo.yumobot.service.impl;


import top.yumoyumo.yumobot.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * The type Redis service.
 *
 * @Author: yumo
 * @Description: redis操作Service的实现类
 * @DateTime: 2022 /7/29 12:13
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean expire(String key, long expire) {
        return stringRedisTemplate.expire(key, expire, TimeUnit.DAYS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Set<String> keys(String match) {

        return stringRedisTemplate.keys(match);
    }

    @Override
    public Cursor<String> scan(String match) {
        Cursor<String> scan = stringRedisTemplate.scan(ScanOptions.scanOptions().match(match).build());
        return scan;
    }
}

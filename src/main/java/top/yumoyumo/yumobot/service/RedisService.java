package top.yumoyumo.yumobot.service;

import org.springframework.data.redis.core.Cursor;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * The interface Redis service.
 */
public interface RedisService {
    /**
     * 存储数据
     *
     * @param key   the key
     * @param value the value
     */
    void set(String key, String value);

    /**
     * 获取数据
     *
     * @param key the key
     * @return the string
     */
    String get(String key);

    /**
     * 设置超期时间
     *
     * @param key    the key
     * @param expire the expire
     * @return the boolean
     */
    boolean expire(String key, long expire);

    /**
     * 删除数据
     *
     * @param key the key
     */
    void remove(String key);

    /**
     * 自增操作
     *
     * @param key   the key
     * @param delta 自增步长
     * @return the long
     */
    Long increment(String key, long delta);

    /**
     * 获取所有key
     *
     * @param match the match
     * @return set
     */
    Set<String> keys(String match);

    /**
     * Scan cursor.
     *
     * @param match the match
     * @return the cursor
     */
    Cursor<String> scan(String match);
}

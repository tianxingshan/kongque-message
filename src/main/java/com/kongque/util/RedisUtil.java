package com.kongque.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * @Author sws
 * @date 2020-03-29
 */
@Repository
public class RedisUtil {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     *设置指定key的失效时间
     * @param key
     * @param time 失效时间
     * @return
     */
    public boolean expire(String key,long time){
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 获取指定课的失效时间
     * @param key 指定的key
     * @return 时间,单位:second
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }


    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key){

        Boolean hasKey = redisTemplate.hasKey(key);
        return  hasKey;
    }

    /*--------------------------------------------------String类型--------------------------------------------*/

    /**
     * 普通缓存放入
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,Object value){

        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}

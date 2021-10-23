package com.example.sb2samples.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author lironghong
 * @date 10:30 2020/7/27
 * email itlironghong@foxmail.com
 * description redis缓存服务
 */
@Service
public class RedisCacheService {
    private final RedisTemplate<String, Serializable> redisTemplate;

    private final StringRedisTemplate stringRedisTemplate;

    public RedisCacheService(RedisTemplate<String, Serializable> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }
    /**
     * redis 对象缓存
     * */
    public <K, V extends Serializable> void redisCache(K k, V v) {
        //如果此key存在就删除用新的
        if (redisTemplate.hasKey(k.toString())) {
            redisTemplate.delete(k.toString());
        }
        redisTemplate.opsForValue().set(k.toString(), v);
    }
    /**
     * redis 对象缓存 缓存过期时间(秒)
     * */
    public <K, V extends Serializable> void redisCacheExpireSeconds(K k, V v,long seconds) {
        redisCache(k,v);
        if (seconds > 0){
            redisTemplate.expire(k.toString(),seconds, TimeUnit.SECONDS);
        }
    }
    /**
     * redis 对象缓存 缓存过期时间(分钟)
     * */
    public <K, V extends Serializable> void redisCacheExpireMinutes(K k, V v,long minutes) {
        redisCache(k,v);
        if (minutes > 0){
            redisTemplate.expire(k.toString(), minutes, TimeUnit.MINUTES);
        }
    }

    /**
     * redis 对象缓存 缓存过期时间(日期)
     * */
    public <K, V extends Serializable> void redisCacheExpireAtDate(K k, V v, Date date) {
        redisCache(k,v);
        if (null != date){
            redisTemplate.expireAt(k.toString(),date);
        }
    }
    /**
     * redis 取值 返回对象
     * */
    public <K, T extends Serializable> T getRedisCache(K k, Class<T> tClass){
        Serializable serializable = redisTemplate.opsForValue().get(k.toString());
        if (null == serializable){
            return null;
        }
        return  JSONObject.toJavaObject(JSONObject.parseObject(serializable.toString()),tClass);
    }


    /**
     *redis 集合缓存
     * */
    public <K, V extends Serializable> void redisArrayCache(K k, Collection<V> array){
        //如果此key存在就删除用新的
        if (redisTemplate.hasKey(k.toString())) {
            redisTemplate.delete(k.toString());
        }
        array.forEach(a-> redisTemplate.opsForList().rightPush(k.toString(),a));

    }

    /**
     * redis 集合缓存 缓存过期时间(秒)
     * */
    public <K, V extends Serializable> void redisArrayCacheExpireSeconds(K k, Collection<V> array,long seconds) {
        redisArrayCache(k,array);
        if (seconds > 0){
            redisTemplate.expire(k.toString(),seconds, TimeUnit.SECONDS);
        }
    }
    /**
     * redis 集合缓存 缓存过期时间(分钟)
     * */
    public <K, V extends Serializable> void redisArrayCacheExpireMinutes(K k, Collection<V> array,long minutes) {
        redisArrayCache(k,array);
        if (minutes > 0){
            redisTemplate.expire(k.toString(),minutes, TimeUnit.MINUTES);
        }
    }

    /**
     * redis 对象缓存 缓存过期时间(日期)
     * */
    public <K, V extends Serializable> void redisArrayCacheAtDate(K k, Collection<V> array, Date date) {
        redisArrayCache(k,array);
        if (null != date){
            redisTemplate.expireAt(k.toString(),date);
        }
    }


    /**
     * redis 取值 返回集合
     * */
    public <K, T extends Serializable> List<T> parseRedisArrayCache(K k, Class<T> tClass){
        List<Serializable> range = redisTemplate.opsForList().range(k.toString(), 0, -1);
        if (CollectionUtils.isEmpty(range)){
            return new ArrayList<>();
        }
        return JSONArray.parseArray(range.toString(),tClass);
    }

    /**
     * stringRedisTemplate k v 都是string
     * */
    public void stringCache(String key,Object value){
        if (stringRedisTemplate.hasKey(key)){
            stringRedisTemplate.delete(key);
        }
        stringRedisTemplate.opsForValue().set(key,value.toString());
    }

    /**
     * 获取stringRedisTemplate 缓存
     * */
    public String getStringCache(String key){
        String value = stringRedisTemplate.opsForValue().get(key);
        if (null == value){
            return null;
        }
        return value;

    }
    /**
     * 获取stringRedisTemplate缓存 string转map
     * */
    public Map<String,Object> getStringCacheCoverToMap(String key){
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        String stringCache = getStringCache(key);
        if (null == stringCache){
            return null;
        }
        Map map = JSON.parseObject(stringCache, Map.class);
        map.forEach((k,v)->{objectObjectHashMap.put(k.toString(),v);});
        return  objectObjectHashMap;
    }

    /**
     * 获取stringRedisTemplate缓存 转json
     * */
    public JSONObject getStringCacheCoverToJson(String key){
        String stringCache = getStringCache(key);
        if (null == stringCache){
            return null;
        }
        return JSONObject.parseObject(stringCache);
    }

    /**
     * redis stringRedisTemplate缓存 缓存过期时间(秒)
     * */
    public void stringCacheExpireSeconds(String key,Object value,long seconds) {
        stringCache(key,value);
        if (seconds > 0){
            stringRedisTemplate.expire(key,seconds, TimeUnit.SECONDS);
        }
    }
    /**
     * redis stringRedisTemplate缓存 缓存过期时间(分钟)
     * */
    public void stringCacheExpireMinutes(String key,Object value,long minutes) {
        stringCache(key,value);
        if (minutes > 0){
            redisTemplate.expire(key,minutes, TimeUnit.MINUTES);
        }
    }

    /**
     * redis stringRedisTemplate缓存 缓存过期时间(日期)
     * */
    public void stringCacheAtDate(String key,Object value, Date date) {
        stringCache(key,value);
        if (null != date){
            redisTemplate.expireAt(key,date);
        }
    }

}

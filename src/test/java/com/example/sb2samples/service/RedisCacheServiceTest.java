package com.example.sb2samples.service;

import com.example.sb2samples.Springboot2SampleRedisApplication;
import com.example.sb2samples.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lironghong
 * @date 10:34 2020/7/27
 * email itlironghong@foxmail.com
 * description redis测试服务
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Springboot2SampleRedisApplication.class)
public class RedisCacheServiceTest {
    @Autowired
    RedisCacheService redisCacheService;


    @Test
    public void test() {
        User user = new User();
        user.setId(1l);
        user.setUsername("maki");
        user.setPassword("1234");
        redisCacheService.redisCache("1",user);

        User redisCache = redisCacheService.getRedisCache("1", User.class);
       System.out.println(redisCache);

    }

    @Test
    public void redisList() {
        Set<User> lists = new HashSet<>();
        User user = new User() {
            {
                setId(2l);
                setUsername("maki1");
                setPassword("1234");
            }
        };
        User user1 = new User() {
            {
                setId(3l);
                setUsername("maki2");
                setPassword("1234");
            }
        };
        lists.add(user);
        lists.add(user1);
//        redisCacheService.redisArrayCache("key",lists);
        redisCacheService.redisArrayCacheExpireSeconds("key",lists,50);
    }

    @Test
    public void getredisList(){
        List<User> key = redisCacheService.parseRedisArrayCache("key", User.class);
        System.out.println(key);

    }

}
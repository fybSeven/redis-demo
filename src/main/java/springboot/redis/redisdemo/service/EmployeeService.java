package springboot.redis.redisdemo.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import springboot.redis.redisdemo.dao.EmployeeDao;
import springboot.redis.redisdemo.entity.Employee;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: fengyibo
 * @Date: 2019/1/11 16:43
 * @Description:
 */
@Service
public class EmployeeService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private EmployeeDao employeeDao;

    public Employee select(String name){
        Employee employee = (Employee) redisTemplate.opsForValue().get(name);
        if (employee != null){
            return employee;
        }
        employee = employeeDao.selet(name);
        redisTemplate.opsForValue().set(name, employee, 1L, TimeUnit.MINUTES);
        return employee;
    }

    /**
     * 分布式锁实现
     * @param key
     * @param expire
     * @return
     */
    public Boolean setNX(String key, int expire){
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            //获取jedis连接
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            //使用set命令 3.0中SetParams封装nx ex(单位是秒)
            String set = jedis.set(key, UUID.randomUUID().toString(), "NX", "EX", expire);
            return StringUtils.isNotBlank(set);
        });
    }

    /**
     * 释放分布式锁
     * @param lock
     * @return
     */
    public Boolean freeLock(String lock){
        return redisTemplate.delete(lock);
    }


}

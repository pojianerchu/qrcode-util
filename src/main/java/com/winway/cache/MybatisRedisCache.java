package com.winway.cache;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.winway.springUtils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author GuoYongMing
 * @Date 2021/1/20 14:11
 * @Version 1.0
 */
@Slf4j
public class MybatisRedisCache implements Cache {

    private String id;

    //读写锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    //RedisTemplate
    private RedisTemplate<String,Object> redisTemplate;
    {
        //RedisTemplate不能使用自动注入，所以使用工具类 BeanFactoryPostProcessor获得
        this.redisTemplate = SpringUtils.getBean(RedisTemplate.class);
    }



    public MybatisRedisCache(final String id){
        if (id == null){
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        if (redisTemplate == null){
            //犹豫启动期间注入失败，只能运行期间注入，这段代码可以删除
            //redisTemplate = (RedisTemplate<String, Object>) ApplicationContextRegister.getApplicationContext().getBean("RedisTemplate");
        }
        if (value != null){
            redisTemplate.opsForValue().set(key.toString(),value);
        }
    }

    @Override
    public Object getObject(Object key) {
        try {
            return redisTemplate.opsForValue().get(key.toString());
        }catch (Exception e){
            log.error("缓存出错！");
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        if (key!=null)
            redisTemplate.delete(key.toString());
        return null;
    }

    @Override
    public void clear() {
        log.debug("清楚缓存");
        if (redisTemplate!=null){
            Set<String> keys = redisTemplate.keys("*:" + this.id + "*");
            if (!CollectionUtils.isEmpty(keys)){
                redisTemplate.delete(keys);
            }
        }

    }

    @Override
    public int getSize() {
        return (int) redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }


    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }



}

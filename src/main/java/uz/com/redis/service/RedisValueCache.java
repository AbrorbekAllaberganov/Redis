package uz.com.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisValueCache {
    private final ValueOperations<String,Object>valueOps;

    public RedisValueCache(final RedisTemplate<String, Object> redisTemplate) {
        valueOps = redisTemplate.opsForValue();
    }

    public void cache(String key,final Object data){
        valueOps.set(key,data);
    }

    public Object getCacheValue(final String key){
        return valueOps.get(key);
    }

    public void deleteCacheValue(final String key){
        valueOps.getOperations().delete(key);
    }

}

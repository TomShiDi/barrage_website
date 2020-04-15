package barrage.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/3/19
 **/
@Component
public class DefaultRedisComponent {
    private StringRedisTemplate stringRedisTemplate;

    private long timeout = 300;

    private String HASHMAP_NAME = "auth_code_map";

    @Autowired
    public DefaultRedisComponent(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public Long setAsList(String key, String value) {
        if (value == null || key == null) {
            return null;
        }
        return stringRedisTemplate.opsForList().rightPush(key, value);
    }

    public boolean setAsKeyValue(String key, String value) {
        return setAsKeyValue(key, value, timeout);
    }

    public boolean setAsKeyValue(String key, String value, long expire) {
        if (value == null || key == null) {
            return false;
        }
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value, expire, TimeUnit.SECONDS);
    }

    public String getStringValue(String key) {
        if (key == null) {
            return null;
        }
        String value = stringRedisTemplate.opsForValue().getAndSet(key, "");
        stringRedisTemplate.opsForValue().getOperations().delete(key);
        return value;
    }

}

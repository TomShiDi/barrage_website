package barrage.demo.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/3/20
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultRedisComponentTest {

    @Autowired
    private DefaultRedisComponent defaultRedisComponent;

    @Test
    public void setAsKeyValue() {
        boolean result = defaultRedisComponent.setAsKeyValue("2020年3月20日", "Hello");
        Assert.assertNotEquals(false, result);
    }

    @Test
    public void getStringValue() {
        String result = defaultRedisComponent.getStringValue("2020年3月20日");
        Assert.assertEquals("Hello", result);
    }
}
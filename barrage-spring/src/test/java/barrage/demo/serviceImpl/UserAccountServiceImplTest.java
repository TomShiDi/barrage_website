package barrage.demo.serviceImpl;

import barrage.demo.entity.UserAccountInfo;
import barrage.demo.service.UserAccountInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAccountServiceImplTest {

    @Autowired
    private UserAccountInfoService userAccountInfoService;

    @Test
    public void findByUserLoginName() {
        UserAccountInfo userAccountInfo = userAccountInfoService.findByUserLoginName("TomShiDi");
        Assert.assertNotNull(userAccountInfo);

    }

    @Test
    public void saveAccountInfo() {
        UserAccountInfo userAccountInfo = new UserAccountInfo();
        userAccountInfo.setUserLoginName("TomShiDi");
        userAccountInfo.setUserPassword("123456");
        UserAccountInfo result = userAccountInfoService.saveAccountInfo(userAccountInfo);
        Assert.assertNotNull(result);

    }
}
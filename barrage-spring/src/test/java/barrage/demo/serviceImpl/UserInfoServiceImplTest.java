package barrage.demo.serviceImpl;

import barrage.demo.entity.UserInfo;
import barrage.demo.enums.AuthEnums;
import barrage.demo.service.UserInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceImplTest {

    @Autowired
    private UserInfoService userInfoService;


    public UserInfoServiceImplTest() {
    }

    @Test
    public void findByUserId() {
        UserInfo userInfo = userInfoService.findByUserId(1);
        Assert.assertNotNull(userInfo);
    }

    @Test
    public void findByNickName() {
        List<UserInfo> userInfoList = userInfoService.findByNickName("TomShiDi");
        Assert.assertNotEquals(0, userInfoList.size());
    }

    @Test
    public void findByUserPhoneNum() {
        UserInfo userInfo = userInfoService.findByUserPhoneNum("123456879");
        Assert.assertNotNull(userInfo);
    }

    @Test
    public void deleteByUserId() {
        userInfoService.deleteByUserId(2);
    }

    @Test
    public void saveUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName("TomShiDi");
        userInfo.setUserSex("男");
        userInfo.setPhoneNum("123456879");
        userInfo.setUserPassword("123456");
        userInfo.setUserEmail("1341109792@qq.com");
        userInfo.setUserDescription("修改测试");
//        userInfo.setUserId(1);
        UserInfo result = userInfoService.saveUserInfo(userInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void updateUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName("TomShiDi");
        userInfo.setUserSex("男");
        userInfo.setPhoneNum("123456879");
        userInfo.setUserEmail("1111111");
        userInfo.setUserDescription("修改测试");
        userInfo.setUserId(172);
        UserInfo result = userInfoService.saveUserInfo(userInfo);
        assertNotNull(result);
    }

    @Test
    public void updateUserAccountStatus() {
        int result = userInfoService.updateUserAccountStatus("1341109792@qq.com ", AuthEnums.STATUS_ACTIVATION.getCode());
        assertNotEquals(result, 0);
    }
}
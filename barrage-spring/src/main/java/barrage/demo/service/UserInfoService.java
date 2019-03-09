package barrage.demo.service;

import barrage.demo.entity.UserInfo;

import java.util.List;

public interface UserInfoService {
    UserInfo findByUserId(Integer userId);

    List<UserInfo> findByNickName(String nickName);

    UserInfo findByUserPhoneNum(String phoneNum);

    void deleteByUserId(Integer userId);

    UserInfo saveUserInfo(UserInfo userInfo);
}

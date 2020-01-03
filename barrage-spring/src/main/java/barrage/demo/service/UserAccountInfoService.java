package barrage.demo.service;

import barrage.demo.entity.UserAccountInfo;

public interface UserAccountInfoService {
    UserAccountInfo findByUserLoginName(String loginName);

    UserAccountInfo saveAccountInfo(UserAccountInfo userAccountInfo);
}

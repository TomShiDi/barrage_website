package barrage.demo.repository;

import barrage.demo.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findByUserId(Integer userId);

    List<UserInfo> findByNickName(String nickName);

    UserInfo findByUserPhoneNum(String userPhoneNum);

    void deleteByUserId(Integer userId);
}

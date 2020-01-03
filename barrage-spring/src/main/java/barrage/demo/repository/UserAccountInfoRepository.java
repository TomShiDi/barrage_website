package barrage.demo.repository;

import barrage.demo.entity.UserAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountInfoRepository extends JpaRepository<UserAccountInfo, String> {
    UserAccountInfo findByUserLoginName(String userLoginName);
}

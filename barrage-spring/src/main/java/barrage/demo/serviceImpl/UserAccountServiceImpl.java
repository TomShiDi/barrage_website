package barrage.demo.serviceImpl;

import barrage.demo.entity.UserAccountInfo;
import barrage.demo.enums.BarrageExceptionEnum;
import barrage.demo.exception.BarrageException;
import barrage.demo.repository.UserAccountInfoRepository;
import barrage.demo.service.UserAccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountInfoService {

    private UserAccountInfoRepository repository;

    @Autowired
    public UserAccountServiceImpl(UserAccountInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserAccountInfo findByUserLoginName(String loginName) {
        UserAccountInfo userAccountInfo = repository.findByUserLoginName(loginName);

        return userAccountInfo;
    }

    @Override
    public UserAccountInfo saveAccountInfo(UserAccountInfo userAccountInfo) {
        UserAccountInfo result = repository.save(userAccountInfo);

        if (result == null) {
            throw new BarrageException(BarrageExceptionEnum.USER_ACCOUNT_INFO_SAVE_ERROR);
        }

        return result;
    }
}

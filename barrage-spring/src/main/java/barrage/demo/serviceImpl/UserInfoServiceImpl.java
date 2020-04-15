package barrage.demo.serviceImpl;

import barrage.demo.entity.UserInfo;
import barrage.demo.enums.BarrageExceptionEnum;
import barrage.demo.exception.BarrageException;
import barrage.demo.repository.UserInfoRepository;
import barrage.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepository repository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserInfo findByUserId(Integer userId) {
        UserInfo userInfo = repository.findByUserId(userId);
        return userInfo;
    }

    @Override
    public List<UserInfo> findByNickName(String nickName) {
        List<UserInfo> userInfoList = repository.findByNickName(nickName);
        return userInfoList;
    }

    @Override
    public UserInfo findByUserPhoneNum(String phoneNum) {
        UserInfo userInfo = repository.findByUserPhoneNum(phoneNum);
        return userInfo;
    }

    @Override
    public UserInfo findByUserEmail(String userEmail) {
        return repository.findByUserEmail(userEmail);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        repository.deleteByUserId(userId);
    }

    @Override
    public UserInfo saveUserInfo(UserInfo userInfo) {
        UserInfo result = repository.save(userInfo);
        if (result == null) {
            throw new BarrageException(BarrageExceptionEnum.USER_INFO_SAVE_ERROR);
        }

        return result;
    }

    @Override
    public UserInfo updateUserInfo(UserInfo userInfo) {
        UserInfo result = repository.findByUserId(userInfo.getUserId());
        if (result == null) {
            throw new BarrageException(BarrageExceptionEnum.USER_INFO_NOT_FOUND);
        }
        return repository.save(userInfo);
    }

    @Override
    public int updateUserAccountStatus(String userEmail, Integer status) {
        return repository.updateUserAccountStatus(userEmail, status);
    }
}

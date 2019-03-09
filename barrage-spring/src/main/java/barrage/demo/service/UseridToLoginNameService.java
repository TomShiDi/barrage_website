package barrage.demo.service;

import barrage.demo.entity.UseridToLoginname;

public interface UseridToLoginNameService {
    UseridToLoginname findByUserId(Integer userId);

    UseridToLoginname findByLoginname(String loginName);

    UseridToLoginname save(UseridToLoginname useridToLoginname);
}

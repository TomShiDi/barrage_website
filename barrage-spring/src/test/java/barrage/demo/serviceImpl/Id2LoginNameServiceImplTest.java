package barrage.demo.serviceImpl;

import barrage.demo.entity.UseridToLoginname;
import barrage.demo.service.UseridToLoginNameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Id2LoginNameServiceImplTest {

    @Autowired
    private UseridToLoginNameService useridToLoginNameService;

    @Test
    public void findByUserId() {
        UseridToLoginname useridToLoginname = useridToLoginNameService.findByUserId(1);
        Assert.assertNotNull(useridToLoginname);

    }

    @Test
    public void findByLoginname() {
        UseridToLoginname useridToLoginname = useridToLoginNameService.findByLoginname("TomShiDi");
        Assert.assertNotNull(useridToLoginname);
    }

    @Test
    public void save() {
        UseridToLoginname useridToLoginname = new UseridToLoginname();
        useridToLoginname.setLoginName("TomShiDi");
        useridToLoginname.setUserId(1);
        UseridToLoginname result = useridToLoginNameService.save(useridToLoginname);
        Assert.assertNotNull(result);

    }
}
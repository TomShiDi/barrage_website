package barrage.demo.serviceImpl;

import barrage.demo.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/3/17
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendSimpleMail() {
        String to = "1341109792@qq.com";
        mailService.sendSimpleMail(to, "邮件发送测试", "这是测试正文内容");
    }

    @Test
    public void sendHtmlMail() {
        String to = "1341109792@qq.com";
//        mailService.sendHtmlMail();
    }

    @Test
    public void sendTemplateMail() {
        String to = "1341109792@qq.com";
        Map<String, Object> params = new HashMap<>();
        params.put("authCode", "123456");
        try {
            mailService.sendTemplateMail(to, "模板邮件发送测试", params);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
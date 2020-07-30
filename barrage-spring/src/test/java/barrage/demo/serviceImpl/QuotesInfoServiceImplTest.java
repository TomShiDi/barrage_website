package barrage.demo.serviceImpl;

import barrage.demo.entity.QuotesInfo;
import barrage.demo.service.QuotesInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @Author TomShiDi
 * @Since 2020/7/30
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class QuotesInfoServiceImplTest {

    @Resource
    private QuotesInfoService quotesInfoService;

    @Test
    public void findByQuotesId() {
        QuotesInfo quotesInfo = quotesInfoService.findByQuotesId(1);
    }

    @Test
    public void getOne() {
        QuotesInfo quotesInfo = quotesInfoService.getOne(0);
        QuotesInfo quotesInfo1 = quotesInfoService.getOne(1);
        Assert.assertNotNull(quotesInfo1);
        Assert.assertNotNull(quotesInfo);
        Assert.assertNotEquals(quotesInfo, quotesInfo1);
    }

    @Test
    public void save() {
        QuotesInfo quotesInfo = new QuotesInfo();
        quotesInfo.setAuthor("tom");
        quotesInfo.setPublisherId(1);
        quotesInfo.setQuotesContent("测试内容");
        quotesInfo.setRecordDate(new Date());
        QuotesInfo result = quotesInfoService.save(quotesInfo);
        Assert.assertNotNull(result);

    }

    @Test
    public void deleteByQuotesId() {
        quotesInfoService.deleteByQuotesId(1);
    }
}
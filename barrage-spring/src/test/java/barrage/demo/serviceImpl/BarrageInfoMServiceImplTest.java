package barrage.demo.serviceImpl;

import barrage.demo.entity.BarrageInfo;
import barrage.demo.service.BarrageInfoMService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


/**
 * @Author TomShiDi
 * @Since 2019/6/10
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BarrageInfoMServiceImplTest {

    @Autowired
    private BarrageInfoMService barrageInfoMService;

    @Test
    public void findByBarrageId() {
        BarrageInfo result = barrageInfoMService.findByBarrageId(26);
        Assert.assertNotNull(result);
    }

    @Test
    public void findBySenderId() {
        List<BarrageInfo> barrageInfoList = barrageInfoMService.findBySenderId(1);
        Assert.assertNotEquals(0, barrageInfoList.size());
    }

    @Test
    public void getBarragePageByIndex() {
        List<BarrageInfo> barrageInfoList = barrageInfoMService.getBarragePageByIndex(PageRequest.of(0, 6));
        Assert.assertNotEquals(0, barrageInfoList.size());
    }

    @Test
    public void saveBarrageInfo() {
        BarrageInfo barrageInfo = new BarrageInfo();
        barrageInfo.setBarrageSenderId(1);
        barrageInfo.setContent("Mybatis测试数据三号");
        barrageInfo.setSpeed(12);
        barrageInfo.setColor("red");
        barrageInfo.setTextSize(20);
        barrageInfo.setStarNum(0);
        barrageInfo.setRoad(2);
        BarrageInfo result = barrageInfoMService.saveBarrageInfo(barrageInfo);

        Assert.assertNotNull(result);
    }

    @Test
    public void update() {
        BarrageInfo barrageInfo = new BarrageInfo();
        barrageInfo.setBarrageId(26);
        barrageInfo.setBarrageSenderId(1);
        barrageInfo.setContent("Mybatis测试数据二号");
        barrageInfo.setSpeed(12);
        barrageInfo.setColor("red");
        barrageInfo.setTextSize(20);
        barrageInfo.setStarNum(0);
        barrageInfo.setBarrageSendTime(new Date());
        barrageInfo.setRoad(2);
        int result = barrageInfoMService.update(barrageInfo);
        Assert.assertNotEquals(0, result);
    }
}
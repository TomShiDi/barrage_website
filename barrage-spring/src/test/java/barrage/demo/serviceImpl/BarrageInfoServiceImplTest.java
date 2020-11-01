package barrage.demo.serviceImpl;

import barrage.demo.entity.BarrageInfo;
import barrage.demo.service.BarrageInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class BarrageInfoServiceImplTest {

    @Autowired
    private BarrageInfoService barrageInfoService;

    @Test
    public void findByBarrageId() {
        BarrageInfo barrageInfo = barrageInfoService.findByBarrageId(14);
        Assert.assertNotNull(barrageInfo);

    }

    @Test
    public void findBySenderId() {
        List<BarrageInfo> barrageInfoList = barrageInfoService.findBySenderId(1);
        Assert.assertNotEquals(0, barrageInfoList.size());
    }

    @Test
    public void saveBarrageInfo() {
        BarrageInfo barrageInfo = new BarrageInfo();
        barrageInfo.setContent("第一条弹幕");
        barrageInfo.setBarrageSenderId(1);
        BarrageInfo result = barrageInfoService.saveBarrageInfo(barrageInfo);

        Assert.assertNotNull(result);
    }

    @Test
    public void deleteBarrageInfo() {
//        barrageInfoService.deleteBarrageInfo(13);

    }

    @Test
    public void getBarragePageByIndex() {
        PageRequest request = PageRequest.of(0, 20);
        Page<BarrageInfo> barrageInfoPage = barrageInfoService.getBarragePageByIndex(request);
        Assert.assertNotEquals(0, barrageInfoPage.getTotalElements());
    }
}
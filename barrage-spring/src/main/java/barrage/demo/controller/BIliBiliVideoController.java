package barrage.demo.controller;

import barrage.demo.dao.BilibiliControllerDao;
import barrage.demo.utils.BilibiliJsonUtil;
import barrage.demo.utils.HttpUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * B站评论提取
 * @author tomshidi
 * @date 2020年11月1日19:54:20
 * @version 1.0
 */
@RestController
@RequestMapping("/bilibili")
@Slf4j
public class BIliBiliVideoController {

    @Value("${bilibiliApiUrl}")
    private String bilibiliApiUrl;

    @Value("${filePath}")
    private String filePath;


    /**
     * 根据avId请求B站接口获取评论数据，并存入文件，高并发下崩溃率很高
     *
     * @param avId 视频的id号
     * @return `
     * @throws Exception `
     */
    @ApiOperation(value = "采集评论信息", notes = "根据视频av号采集评论")
    @ApiImplicitParam(name = "avId", value = "待采集视频id", required = true, dataTypeClass = String.class)
    @GetMapping("/get-comment")
    public Object getComments(@RequestParam(name = "avId") String avId) throws Exception {
        int times = 0;
        float nullTimes = 0;
        String requestUrl = bilibiliApiUrl + "?oid=" + avId + "&type=1&pn=";
        String requestData = null;
        BilibiliControllerDao bilibiliControllerDao = new BilibiliControllerDao();

        BilibiliJsonUtil.createFileDelete(avId);
        while ((requestData = HttpUtil.doGet(requestUrl + "1")) == null) {
            log.info("初始请求为空，将再次尝试");
        }
        BilibiliJsonUtil.writeToFile(BilibiliJsonUtil.jsonParse(requestData));
        if (BilibiliJsonUtil.size <= 0) {
            bilibiliControllerDao.setCode(500);
            bilibiliControllerDao.setMessage("当前视频没有评论");
            return bilibiliControllerDao;
        }
        times = BilibiliJsonUtil.count / BilibiliJsonUtil.size + 1;
        for (int i = 2; i <= times - 1; i++) {
            requestData = HttpUtil.doGet(requestUrl + i);
            if (requestData == null) {
                log.info("当前请求为空，序号为: {}", i);
                nullTimes++;
                continue;
            }
            BilibiliJsonUtil.writeToFile(BilibiliJsonUtil.jsonParse(requestData));
        }
        log.info("请求数据为空的比例: {}%", Math.floor((nullTimes / times) * 10000) / 100);
        bilibiliControllerDao.setCode(200);
        bilibiliControllerDao.setMessage("/info/" + avId + ".txt");
        return bilibiliControllerDao;
    }
}

package barrage.demo.controller;

import barrage.demo.dao.BilibiliControllerDao;
import barrage.demo.utils.BilibiliJsonUtil;
import barrage.demo.utils.HttpUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bilibili")
public class BIliBiliVideoController {

    @Value("${bilibiliApiUrl}")
    private String bilibiliApiUrl;

    @Value("${filePath}")
    private String filePath;


    /**
     * 根据avId请求B站接口获取评论数据，并存入文件，高并发下崩溃率很高
     * @param avId 视频的id号
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "采集评论信息", notes = "根据视频av号采集评论")
    @ApiImplicitParam(name = "avId", value = "待采集视频id", required = true, dataTypeClass = String.class)
    @GetMapping("/get-comment")
    public Object getComments(@RequestParam(name = "avId") String avId) throws Exception {
        int times;
        String requestUrl = bilibiliApiUrl + "?oid=" + avId + "&type=1&pn=";
        String requestData = null;
        BilibiliControllerDao bilibiliControllerDao = new BilibiliControllerDao();
//        if (BilibiliJsonUtil.isFileValid(avId)){
//            bilibiliControllerDao.setCode(200);
//            bilibiliControllerDao.setMessage("/info/" + avId + ".txt");
//            return bilibiliControllerDao;
//        }
        BilibiliJsonUtil.createFileDelete(avId);
        requestData = HttpUtil.doGet(requestUrl + "1");
        BilibiliJsonUtil.writeToFile(BilibiliJsonUtil.jsonParse(requestData));
        times = BilibiliJsonUtil.count / BilibiliJsonUtil.size + 1;
        for (int i = 2; i <= times - 1; i++) {
            //TODO 问题记录,循环中执行http请求,似乎不是阻塞执行，待验证
//            Thread.sleep(100);
            requestData = HttpUtil.doGet(requestUrl + i);
            BilibiliJsonUtil.writeToFile(BilibiliJsonUtil.jsonParse(requestData));
        }


        bilibiliControllerDao.setCode(200);
        bilibiliControllerDao.setMessage("/info/" + avId + ".txt");
        return bilibiliControllerDao;
    }
}

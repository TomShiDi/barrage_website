package barrage.demo.controller;

import barrage.demo.dao.BarrageDao;
import barrage.demo.entity.BarrageInfo;


import barrage.demo.entity.UserAccountInfo;
import barrage.demo.enums.BarrageExceptionEnum;
import barrage.demo.exception.BarrageException;
import barrage.demo.service.BarrageInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/barrage")
public class BarrageInfoController {

    private BarrageInfoService barrageInfoService;

    @Autowired
    public BarrageInfoController(BarrageInfoService barrageInfoService) {
        this.barrageInfoService = barrageInfoService;
    }

    /**
     * 弹幕信息保存接口
     * @param barrageInfo 提交的表单实体
     * @param bindingResult 表单验证结果
     * @param map 拓展map
     * @return BarrageDao 展示数据类
     */
    @PostMapping("/save")
    public BarrageDao save(@Valid BarrageInfo barrageInfo,
                            BindingResult bindingResult,
                            Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            BarrageDao barrageDao = new BarrageDao();
            barrageDao.setMessage(bindingResult.getFieldError().getDefaultMessage());
            return barrageDao;
        }

        barrageInfoService.saveBarrageInfo(barrageInfo);

        BarrageDao barrageDao = new BarrageDao();
        barrageDao.setMessage("success");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("barrageInfo", barrageInfo);
        barrageDao.setResultData(resultMap);

        return barrageDao;
    }

    @GetMapping("/getData")
    public BarrageDao getIndexPage(@RequestParam(name = "index",defaultValue = "1") Integer index,
                                   @RequestParam(name = "size",defaultValue = "20") Integer size) {
        BarrageDao barrageDao = new BarrageDao();
        PageRequest request = PageRequest.of(index - 1, size);
        Page<BarrageInfo> barrageInfoPage = barrageInfoService.getBarragePageByIndex(request);
        if (barrageInfoPage == null || barrageInfoPage.getTotalElements() == 0) {
//            barrageDao.setMessage("获取弹幕失败");
//            return barrageDao;
            throw new BarrageException(BarrageExceptionEnum.GET_BARRAGE_ERROR);
        }else {
            barrageDao.setMessage("success " + "index: " + index);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("barrageInfoPage", barrageInfoPage);
            barrageDao.setResultData(resultMap);
            return barrageDao;
        }
    }

    @GetMapping("/star")
    public BarrageDao starBarrage(@RequestParam(name = "barrageId") Integer barrageId) {
        BarrageInfo barrageInfo = barrageInfoService.findByBarrageId(barrageId);
        barrageInfo.setStarNum(barrageInfo.getStarNum() + 1);
        barrageInfoService.saveBarrageInfo(barrageInfo);

        BarrageDao barrageDao = new BarrageDao();
        barrageDao.setMessage("success");
        return barrageDao;
    }

}

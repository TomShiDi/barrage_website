package barrage.demo.controller;

import barrage.demo.dao.BarrageDao;
import barrage.demo.entity.BarrageInfo;


import barrage.demo.entity.UserAccountInfo;
import barrage.demo.enums.BarrageExceptionEnum;
import barrage.demo.exception.BarrageException;
import barrage.demo.service.BarrageInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

//@Api(value = "弹幕信息操作接口")
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
    @ApiOperation(value = "保存用户发送的弹幕", notes = "", response = BarrageDao.class)
    @ApiImplicitParam(name = "barrageInfo", value = "弹幕信息form数据", dataTypeClass = BarrageInfo.class, required = true)
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

    @ApiOperation(value = "获取指定页码数的弹幕数据",  response = BarrageDao.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "index", value = "待取弹幕数据的页码", defaultValue = "1", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每一页的数据条数", defaultValue = "10", dataTypeClass = Integer.class, paramType = "query")
    })
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

    @ApiOperation(value = "给指定弹幕点赞", response = BarrageDao.class)
    @ApiImplicitParam(name = "barrageId", value = "弹幕id", required = true, dataTypeClass = Integer.class)
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

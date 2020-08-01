package barrage.demo.controller;

import barrage.demo.builders.ResponseDtoBuilder;
import barrage.demo.dto.QuotesInfoDto;
import barrage.demo.dto.QuotesResponseDto;
import barrage.demo.entity.QuotesInfo;
import barrage.demo.entity.UserInfo;
import barrage.demo.enums.AuthEnums;
import barrage.demo.enums.BarrageExceptionEnum;
import barrage.demo.exception.BarrageException;
import barrage.demo.service.QuotesInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author TomShiDi
 * @Since 2020/7/27
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping(value = "/quotes")
public class QuotesController {

    @Resource
    private QuotesInfoService quotesInfoService;

    @RequestMapping(value = "/getOne")
    public QuotesResponseDto getOne(@RequestParam(name = "index") Integer index) {
        if (index < 0) {
            throw new BarrageException(BarrageExceptionEnum.INDEX_OUT_OF_BOUND);
        }
        QuotesInfo quotesInfo = quotesInfoService.getOne(index);
        return (QuotesResponseDto) new ResponseDtoBuilder<>(QuotesResponseDto.class)
                .code(200)
                .message("查询成功")
                .data(quotesInfo)
                .build();
    }

    @PostMapping("/save")
    public QuotesResponseDto save(@Valid QuotesInfoDto quotesInfoDto,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BarrageException(BarrageExceptionEnum.LOGIN_INFO_INVALID.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        QuotesInfo quotesInfo = new QuotesInfo();
        BeanUtils.copyProperties(quotesInfoDto,quotesInfo);
        if (userInfo == null) {
            throw new BarrageException(AuthEnums.AUTH_NOT_LOGIN);
        }else {
            quotesInfo.setPublisherId(userInfo.getUserId());
        }

        QuotesInfo result = quotesInfoService.save(quotesInfo);
        return (QuotesResponseDto) new ResponseDtoBuilder<>(QuotesResponseDto.class)
                .code(200)
                .message("名言警句存储成功")
                .data(result)
                .build();
    }

    @GetMapping(value = "/findById")
    public QuotesResponseDto findByQuotesId(@RequestParam(name = "quotesId") Integer quotesId) {
        if (quotesId < 0) {
            throw new BarrageException(BarrageExceptionEnum.INDEX_OUT_OF_BOUND);
        }

        QuotesInfo result = quotesInfoService.findByQuotesId(quotesId);
        return (QuotesResponseDto) new ResponseDtoBuilder<>(QuotesResponseDto.class)
                .code(200)
                .message("查询成功")
                .data(result)
                .build();
    }
}

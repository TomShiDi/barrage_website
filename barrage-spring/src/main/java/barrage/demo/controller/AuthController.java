package barrage.demo.controller;

import barrage.demo.builders.ResponseDtoBuilder;
import barrage.demo.dao.AuthResponseDto;
import barrage.demo.dto.LoginInfoDto;
import barrage.demo.entity.UserInfo;
import barrage.demo.enums.AuthEnums;
import barrage.demo.enums.BarrageExceptionEnum;
import barrage.demo.redis.DefaultRedisComponent;
import barrage.demo.service.UserInfoService;
import barrage.demo.utils.CookieUtil;
import barrage.demo.utils.RandomKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.DigestUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/3/25
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private UserInfoService userInfoService;

    private DefaultRedisComponent defaultRedisComponent;

    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${login.userKey}")
    private String userKey;

    private String SESSON_USERINFO = "userInfo";


    @Autowired
    public AuthController(UserInfoService userInfoService, DefaultRedisComponent defaultRedisComponent, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userInfoService = userInfoService;
        this.defaultRedisComponent = defaultRedisComponent;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 登录操作的前置方法
     * 消息队列生产者
     * @param loginInfoDto 登录信息
     * @param bindingResult 校验类
     * @return 返回处理结果
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public AuthResponseDto preLogin(@Valid LoginInfoDto loginInfoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (AuthResponseDto) new ResponseDtoBuilder<>(AuthResponseDto.class)
                    .enumSet(BarrageExceptionEnum.LOGIN_INFO_INVALID)
                    .build();
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        int result = judge(loginInfoDto,attributes.getRequest());
        if (result == AuthEnums.FALSE.getCode()) {
            return (AuthResponseDto) new ResponseDtoBuilder<>(AuthResponseDto.class)
                    .enumSet(AuthEnums.INCORRECT_ACCOUNT_INFO)
                    .data(loginInfoDto)
                    .build();
        }

        if (result == AuthEnums.STATUS_NO_ACTIVATION.getCode()) {
            return (AuthResponseDto) new ResponseDtoBuilder<>(AuthResponseDto.class)
                    .enumSet(AuthEnums.STATUS_NO_ACTIVATION)
                    .data(loginInfoDto)
                    .build();
        }

        if (result == AuthEnums.STATUS_BANNED.getCode()) {
            return (AuthResponseDto) new ResponseDtoBuilder<>(AuthResponseDto.class)
                    .enumSet(AuthEnums.STATUS_BANNED)
                    .data(loginInfoDto)
                    .build();
        }


        String loginToken = RandomKeyUtil.getUniqueUuid();
        CookieUtil.setLoginCookie(attributes.getResponse(), loginInfoDto.getUserName(), loginToken);
//        DigestUtils.md5Digest((userKey + userInfo.getUserPassword()).getBytes());
        defaultRedisComponent.setAsKeyValue(loginInfoDto.getUserName(), loginToken);
//        SendResult<String, Object> result = null;
//        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("login_topic", loginInfoDto);
//        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                logger.warn("消息队列添加失败: {}", ex.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, Object> result) {
//                logger.info("消息队列添加成功: {}", result.toString());
//            }
//        });
//        try {
//            result = future.get(5000, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException | ExecutionException | TimeoutException e) {
//            e.printStackTrace();
//        }
        return (AuthResponseDto) new ResponseDtoBuilder<>(AuthResponseDto.class)
                .data(loginInfoDto)
                .enumSet(BarrageExceptionEnum.LOGIN_SUCCESS)
                .build();
    }

    /**
     * 消息队列消费方法
     * 登录校验
     * @param loginInfoDto 登录信息数据类
     */
//    @KafkaListener(id = "login_1",topics = "login_topic")
//    public void login(LoginInfoDto loginInfoDto) {
//        if (!judge(loginInfoDto)) {
//            return;
//        }
//
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        String loginToken = RandomKeyUtil.getUniqueUuid();
//        CookieUtil.setLoginCookie(attributes.getResponse(), loginInfoDto.getUserName(), loginToken);
//
////        DigestUtils.md5Digest((userKey + userInfo.getUserPassword()).getBytes());
//        defaultRedisComponent.setAsKeyValue(loginInfoDto.getUserName(), loginToken);
//    }

    private int judge(LoginInfoDto loginInfoDto, HttpServletRequest request) {
        UserInfo origin = userInfoService.findByUserEmail(loginInfoDto.getUserName());
        if (origin == null) {
            return AuthEnums.FALSE.getCode();
        }
        if(!(origin.getUserEmail().equals(loginInfoDto.getUserName())
                && origin.getUserPassword().equals(loginInfoDto.getPassword()))){
            return AuthEnums.FALSE.getCode();
        }
        if (AuthEnums.STATUS_NO_ACTIVATION.getCode().equals(origin.getUserStatus())) {
            return AuthEnums.STATUS_NO_ACTIVATION.getCode();
        }
        if (AuthEnums.STATUS_BANNED.getCode().equals(origin.getUserStatus())) {
            return AuthEnums.STATUS_BANNED.getCode();
        }
        request.getSession().setAttribute(SESSON_USERINFO, origin);
        return AuthEnums.TRUE.getCode();
    }
}

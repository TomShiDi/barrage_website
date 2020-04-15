package barrage.demo.controller;

import barrage.demo.builders.ResponseDtoBuilder;
import barrage.demo.dao.AuthResponseDto;
import barrage.demo.dto.LoginInfoDto;
import barrage.demo.dto.SendResultDto;
import barrage.demo.entity.UserInfo;
import barrage.demo.enums.BarrageExceptionEnum;
import barrage.demo.redis.DefaultRedisComponent;
import barrage.demo.service.UserInfoService;
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


    @Autowired
    public AuthController(UserInfoService userInfoService, DefaultRedisComponent defaultRedisComponent, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userInfoService = userInfoService;
        this.defaultRedisComponent = defaultRedisComponent;
        this.kafkaTemplate = kafkaTemplate;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public AuthResponseDto preLogin(@Valid LoginInfoDto loginInfoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (AuthResponseDto) new ResponseDtoBuilder<>(AuthResponseDto.class)
                    .enumSet(BarrageExceptionEnum.LOGIN_INFO_INVALID)
                    .build();
        }
        SendResult<String, Object> result = null;
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("login_topic", loginInfoDto);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.warn("消息队列添加失败: {}", ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                logger.info("消息队列添加成功: {}", result.toString());
            }
        });
        try {
            result = future.get(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return (AuthResponseDto) new ResponseDtoBuilder<>(AuthResponseDto.class)
                .data(result.toString())
                .enumSet(BarrageExceptionEnum.LOGIN_SUCCESS)
                .build();
    }

    //    @KafkaListener(id = "login_1",topics = "login_topic")
    public void login(LoginInfoDto loginInfoDto) {
        if (!judge(loginInfoDto)) {
            return;
        }
//        DigestUtils.md5Digest((userKey + userInfo.getUserPassword()).getBytes());
        defaultRedisComponent.setAsKeyValue(loginInfoDto.getUserName(), RandomKeyUtil.getUniqueUuid());
    }

    private boolean judge(LoginInfoDto loginInfoDto) {
        UserInfo origin = userInfoService.findByUserEmail(loginInfoDto.getUserName());
        if (origin == null) {
            return false;
        }
        return origin.getUserEmail().equals(loginInfoDto.getUserName()) && origin.getUserPassword().equals(loginInfoDto.getPassword());
    }
}

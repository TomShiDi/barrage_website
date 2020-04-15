package barrage.demo.controller;

import barrage.demo.builders.ResponseDtoBuilder;
import barrage.demo.dto.RegisterResponseDto;
import barrage.demo.entity.UserInfo;
import barrage.demo.enums.BarrageExceptionEnum;
import barrage.demo.service.UserInfoService;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/4/15
 **/
@RestController()
@RequestMapping("/register")
public class RegisterController {

    private final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    private UserInfoService userInfoService;

    private StringRedisTemplate stringRedisTemplate;

    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${register-topic}")
    private String registerTopic;

    @Autowired
    public RegisterController(UserInfoService userInfoService, StringRedisTemplate stringRedisTemplate, KafkaTemplate<String, Object> kafkaTemplate) {
        this.userInfoService = userInfoService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @RequestMapping(value = "/do", method = RequestMethod.POST)
    public RegisterResponseDto register(@Valid UserInfo userInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (RegisterResponseDto) new ResponseDtoBuilder<RegisterResponseDto>(RegisterResponseDto.class)
                    .message(bindingResult.getFieldError().getDefaultMessage())
                    .code(BarrageExceptionEnum.LOGIN_INFO_INVALID.getCode())
                    .build();
        }
        UserInfo userInfoDto = new UserInfo();
        BeanUtils.copyProperties(userInfo, userInfoDto, "userStatus");
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(registerTopic, userInfoDto);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.warn("kafka消息发送失败: {}", ex.getMessage());
                throw new RuntimeException(ex);
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                logger.info("kafka消息发送成功: {}", result.toString());
            }
        });

        return (RegisterResponseDto) new ResponseDtoBuilder<>(RegisterResponseDto.class)
                .enumSet(BarrageExceptionEnum.REGISTER_SUCCESS)
                .build();
    }
}

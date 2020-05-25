package barrage.demo.controller;

import barrage.demo.builders.ResponseDtoBuilder;
import barrage.demo.dao.ChatRoomDao;
import barrage.demo.dao.CommonDto;
import barrage.demo.enums.ChatRoomEnum;
import barrage.demo.utils.CookieUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@PropertySource("classpath:application.yml")
public class ChatRoomController {

    @Value("${socket-url}")
    private String socketUrl;

    @Value("${login-url}")
    private String loginUrl;

    /**
     * 用户注册nickname，只是简单的验证然后将nickname加到response的cookie中，失效时间是1小时
     *
     * @param nickName 临时网名
     * @return
     */
    @GetMapping("/register")
    public String register(@RequestParam(name = "nickName") String nickName) {
        Gson gson = new Gson();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        CookieUtil.setCookieNickName(attributes.getResponse(), nickName);
        return gson.toJson(new ChatRoomDao()
                .message("success")
                .code(ChatRoomEnum.SUCCESS.getCode())
                .confirmNickName(nickName));//返回处理结果
    }

    @GetMapping("/init")
    public CommonDto getInitData() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("socketUrl", socketUrl);
        map.put("loginUrl", loginUrl);
        return new ResponseDtoBuilder<CommonDto>(CommonDto.class)
                .code(ChatRoomEnum.SUCCESS.getCode())
                .message("socket链接")
                .data(map)
                .build();
    }

}

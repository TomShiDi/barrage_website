package barrage.demo.controller;

import barrage.demo.dao.ChatRoomDao;
import barrage.demo.enums.ChatRoomEnum;
import barrage.demo.utils.CookieUtil;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    @GetMapping("/register")
    public String register(@RequestParam(name = "nickName") String nickName) {
        Gson gson = new Gson();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        CookieUtil.setCookieNickName(attributes.getResponse(), nickName);
        return gson.toJson(new ChatRoomDao()
                .message("success")
                .code(ChatRoomEnum.SUCCESS.getCode())
                .confirmNickName(nickName));
    }

}

package barrage.demo.controller;

import barrage.demo.entity.WxProperties;
import barrage.demo.utils.WxJsonConvertUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @Author TomShiDi
 * @Since 2020/5/30
 * @Version 1.0
 */
@Controller()
@RequestMapping("/wx")
public class WxAuthController {

    @Value("${wx.code-url}")
    private String codeUrl;

    @Value("${wx.access-url}")
    private String accessUrl;

    @Autowired
    private WxProperties wxProperties;

    private Map<String, String> accessMap;

    @RequestMapping("/auth")
    public void getCode() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(codeUrl)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        System.out.println(response.body().string());
    }

    @RequestMapping("/code")
    @ResponseBody
    public String codeReturn(@RequestParam(name = "code")String code) throws IOException{
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(wxProperties.getAccessUrl().replace("!", code))
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        Map<String, String> map = WxJsonConvertUtil.String2Map(response.body().string());
        if (map.get("access_token") == null) {
            return map.toString();
        }
        accessMap = map;
        return map.toString();
    }
}

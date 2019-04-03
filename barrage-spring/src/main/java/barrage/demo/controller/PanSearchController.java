package barrage.demo.controller;

import barrage.demo.utils.HttpUtil;
import com.google.gson.Gson;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pan")
public class PanSearchController {

    @RequestMapping("/search")
    public String panSearch(@RequestParam(name = "q",defaultValue = "11")String q,
                            @RequestParam(name = "p",defaultValue = "1")Integer p) {
        Gson gson = new Gson();
        q = URLEncoder.encode(q);
        String url = "http://106.15.195.249:8011/search_new?q=" + q + "&p=" + p;
        return HttpUtil.doGet(url);
//        Map<String, Object> map = new HashMap<>();
//        map = gson.fromJson(result, map.getClass());
//        Object object = ((Map<String, Object>) (map.get("list"))).get("data");

//        return result;
    }
}

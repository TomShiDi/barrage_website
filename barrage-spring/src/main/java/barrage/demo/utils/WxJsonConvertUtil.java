package barrage.demo.utils;

import com.fasterxml.jackson.databind.type.ReferenceType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author TomShiDi
 * @Since 2020/5/30
 * @Version 1.0
 */
public class WxJsonConvertUtil {

    public static Map<String, String> String2Map(String originStr) {
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(originStr, new TypeToken<HashMap<String,String>>(){}.getType());
        return map;
    }
}

package barrage.demo.utils;

import barrage.demo.constance.CookieConstance;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {

    public static void setCookieNickName(HttpServletResponse response, String nickName) {
        Cookie cookie = new Cookie(CookieConstance.cookieName, nickName);
        cookie.setMaxAge(CookieConstance.maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<>();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            cookieMap.put(cookie.getName(), cookie);
        }
        return cookieMap;
    }
}

package barrage.demo.aspect;

import barrage.demo.constance.CookieConstance;
import barrage.demo.enums.AuthEnums;
import barrage.demo.exception.BarrageException;
import barrage.demo.redis.DefaultRedisComponent;
import barrage.demo.utils.CookieUtil;
import barrage.demo.utils.DateFormatUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@PropertySource({"classpath:application.yml"})
public class BaseAspect {

    private DefaultRedisComponent defaultRedisComponent;

    @Value("${allowedOrigin}")
    private String allowedOrigin;

    @Value("${ipRecordFilePath}")
    private String ipRecordFile;

    @Value("${index-url}")
    private String indexUrl;

    @Value("${login-url}")
    private String loginUrl;

    @Autowired
    public BaseAspect(DefaultRedisComponent defaultRedisComponent) {
        this.defaultRedisComponent = defaultRedisComponent;
    }

//    "&& !execution(public * barrage.demo.controller.AuthController.*(..))"
@Pointcut(value = "execution(public * barrage.demo.controller.*.*(..)) " +
        "&& !execution(public * barrage.demo.controller.TestController.*(..))" +
        "&& !execution(public * barrage.demo.controller.RegisterController.*(..))" +
        "&& !execution(public * barrage.demo.controller.AuthController.*(..))" +
        "&& !execution(public * barrage.demo.controller.BarrageInfoController.getIndexPage(..))" +
        "&& !execution(public * barrage.demo.controller.IndexController.*(..))" +
        "&& !execution(public * barrage.demo.controller.WxAuthController.*(..))"
        + "&& !execution(public * barrage.demo.controller.QuotesController.getOne(..))"
)
    public void basePointCut() {

    }

    @Before("basePointCut()")
    public void changeOriginHead(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        String origin = request.getHeader("Origin");
        String ip = null;
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-natapp-ip");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && !ip.isEmpty()) {
            writeIpToFile(ip, request.getRequestURI());
        }

        judgeLoginStatus(request, response);
    }

    private void writeIpToFile(String ip,String path) throws Exception {
        File file = new File(ipRecordFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//        System.getProperty("line.separator")
        String newline = System.getProperty("line.separator");
        bufferedOutputStream.write((DateFormatUtil.toChinaNormal(new Date()) + ":\t " + ip + "--->" + path + newline).getBytes());
        bufferedOutputStream.close();
        fileOutputStream.close();
    }

    private void judgeLoginStatus(HttpServletRequest request,HttpServletResponse response) throws Exception{
//        List<Map.Entry<String, Cookie>> entryList = CookieUtil.getAuthCookies(request);
        String email = null;
        String redisToken = null;
        String cookieToken = null;

        Cookie cookieEmail = CookieUtil.getCookie(request, CookieConstance.COOKIE_EMAIL_NAME);
        Cookie cookieLk = CookieUtil.getCookie(request, CookieConstance.LOGIN_COOKIE_NAME);
        email = cookieEmail == null ? null : cookieEmail.getValue();
        redisToken = defaultRedisComponent.getStringValue(email);
        cookieToken = cookieLk == null ? null : cookieLk.getValue();
        System.out.println("path:" + request.getRequestURI());
        if (redisToken == null || !redisToken.equals(cookieToken)) {
//            response.sendRedirect(indexUrl);
            throw new BarrageException(AuthEnums.AUTH_NOT_LOGIN,loginUrl);
        }
    }
}


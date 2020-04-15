package barrage.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

@Aspect
@Component
@PropertySource({"classpath:application.yml"})
public class BaseAspect {

    @Value("${allowedOrigin}")
    private String allowedOrigin;

    @Value("${ipRecordFilePath}")
    private String ipRecordFile;


    @Pointcut("execution(public * barrage.demo.controller.*.*(..))")
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
            writeIpToFile(ip);
        }
    }

    private void writeIpToFile(String ip) throws Exception {
        File file = new File(ipRecordFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//        System.getProperty("line.separator")
        String newline = System.getProperty("line.separator");
        bufferedOutputStream.write((new Date().toString() + ": " + ip + newline).getBytes());
        bufferedOutputStream.close();
        fileOutputStream.close();
    }
}

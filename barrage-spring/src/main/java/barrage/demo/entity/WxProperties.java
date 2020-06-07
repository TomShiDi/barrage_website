package barrage.demo.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author TomShiDi
 * @Since 2020/5/30
 * @Version 1.0
 */
@Component
@ConfigurationProperties(prefix = "wx")
public class WxProperties {
//    @Value("${appid}")
    private String appid;

//    @Value("${appsecret}")
    private String appSecret;

//    @Value("${code-url}")
    private String codeUrl;

//    @Value("${secret-url")
    private String accessUrl;


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }
}

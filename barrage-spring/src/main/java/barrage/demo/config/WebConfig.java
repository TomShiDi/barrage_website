package barrage.demo.config;

import barrage.demo.utils.BilibiliJsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource({"classpath:application.yml"})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${filePath}")
    private String resourceLocation;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
//        registry.addResourceHandler("/info/**").addResourceLocations("file:D:/bilibiliComment/");
        registry.addResourceHandler("/info/**").addResourceLocations("file:" + resourceLocation + "/");
        registry.addResourceHandler("/.well-known/pki-validation/**").addResourceLocations("file:/usr/valid/");
        BilibiliJsonUtil.setFilePath(resourceLocation);
    }
}

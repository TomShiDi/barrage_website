package barrage.demo;

import barrage.demo.methodlog.EnableMethodLog;
import barrage.demo.methodlog.OriginalPeople;
import barrage.demo.nettyPro.HBSocketServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Author TomShiDi
 */
@SpringBootApplication
@EnableJpaAuditing
//@EnableSwagger2
//@MapperScan("barrage.demo.mapper")
//@MapperScan("barrage.demo.mapper")
@EnableMethodLog(scannedPkgName = "barrage.demo.methodlog")
public class DemoApplication implements ApplicationContextAware {


    private static ApplicationContext context;

//    private static OriginalPeople originalPeople;


    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class, args);
//        originalPeople.saySomething("Hello World");
        new HBSocketServer(9999).start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

}

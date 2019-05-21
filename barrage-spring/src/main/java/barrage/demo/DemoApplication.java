package barrage.demo;

import barrage.demo.config.MethodLogConfig;
import barrage.demo.methodLog.OriginalPeople;
import barrage.demo.nettyPro.HBSocketServer;
import barrage.demo.nettyUse.WebSocketServer;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableSwagger2
public class DemoApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(DemoApplication.class, args);
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MethodLogConfig.class);
//        OriginalPeople originalPeople = (OriginalPeople) applicationContext.getBean(OriginalPeople.class.getName());
//        originalPeople.saySomething("Hello World");
//        originalPeople.saySomething("Hello World");
        new HBSocketServer(9999).start();
    }


//    @Bean
//    public TomcatServletWebServerFactory servletContainer(){
//        TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory(){
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint=new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");//confidential
//                SecurityCollection collection=new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        return tomcat;
//    }
//
//    @Bean
//    public Connector httpConnector(){
//        Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(8080);
//        connector.setSecure(false);
//        connector.setRedirectPort(443);
//        return connector;
//    }

}

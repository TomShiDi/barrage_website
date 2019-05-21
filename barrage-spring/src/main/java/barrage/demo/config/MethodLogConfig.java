package barrage.demo.config;

import barrage.demo.methodLog.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.Set;

@Configuration
@PropertySource({"classpath:application.yml"})
@EnableMethodLog(scannedPkgName = "barrage.demo")
public class MethodLogConfig {

//    @Value("${defaultScannedPackage}")
//    private String defaultScannedPackage;
//
//    @Bean
//    @Scope("singleton")
//    public DefaultProxyFactory defaultProxyFactory() {
//        return new DefaultProxyFactory(annotationParserMeta());
//    }
//
//    private AnnotationParserMeta annotationParserMeta() {
//        return new ProxyMethodLogAnnotationParser(getCandidateClass(), MethodLog.class);
//    }
//
//    private Set<Class<?>> getCandidateClass() {
//        AnnotationScannedMeta annotationScannedMeta = new AnnotationScannedFullAchieve();
//        return annotationScannedMeta.scannedCandidates(defaultScannedPackage);
//    }
}

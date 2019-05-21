package barrage.demo.methodLog;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.Set;

@PropertySource({"classpath:application.yml"})
public class MethodLogResourceRegistry implements ImportBeanDefinitionRegistrar {

//    @Value("${defaultScannedPackage}")
//    private String defaultScannedPackage;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableMethodLog.class.getName());
        String pkgName = (String) attributes.get("scannedPkgName");
        AnnotationScannedMeta annotationScannedMeta = new AnnotationScannedFullAchieve();
        AnnotationParserMeta annotationParserMeta = new ProxyMethodLogAnnotationParser(annotationScannedMeta.scannedCandidates(pkgName), MethodLog.class);
        Set<Class<?>> pointedClass = annotationParserMeta.getPointedClasses();
        DefaultProxyFactory defaultProxyFactory = new DefaultProxyFactory(annotationParserMeta);

        if (pointedClass != null && pointedClass.size() > 0) {
            pointedClass.forEach(e->{
                Class<?> proxyClass = defaultProxyFactory.getInstance(e).getClass();
                registry.registerBeanDefinition(e.getName(), BeanDefinitionBuilder.rootBeanDefinition(proxyClass).getBeanDefinition());
            });
        }
    }
}

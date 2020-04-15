package barrage.demo.methodlog.registrys;


import barrage.demo.methodlog.core.AnnotationScannedFullAchieve;
import barrage.demo.methodlog.core.AnnotationScannedMeta;
import barrage.demo.methodlog.core.AnnotationParserMeta;
import barrage.demo.methodlog.core.ProxyMethodLogAnnotationParser;
import barrage.demo.methodlog.utils.BeanNameUtil;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.Set;

/**
 * 动态注册实现类
 *
 * @Author TomShiDi
 */
public class MethodLogResourceRegistry implements ImportBeanDefinitionRegistrar {

    private final static String ANNOTATION_ATTRIBUTE_NAME_PKG = "scannedPkgName";


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //获取EnableMethodLog注解的参数map
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(barrage.demo.methodlog.EnableMethodLog.class.getName());
        //获取scannedPkgName这个参数的值
        String pkgName = (String) attributes.get(ANNOTATION_ATTRIBUTE_NAME_PKG);
        //扫描功能实现类
        AnnotationScannedMeta annotationScannedMeta = new AnnotationScannedFullAchieve();
        //过滤出带有@MethodLog注解的类
        AnnotationParserMeta annotationParserMeta = new ProxyMethodLogAnnotationParser(annotationScannedMeta.scannedCandidates(pkgName), barrage.demo.methodlog.MethodLog.class);
        Set<Class<?>> pointedClass = annotationParserMeta.getPointedClasses();

        //以下为动态注册的核心
        if (pointedClass != null && pointedClass.size() > 0) {
            pointedClass.forEach(e -> {
                String temp = BeanNameUtil.parseToBeanName(e.getName());
                //首先判断是否已经注册过，这里可有可无，因为设计这个判断的最初目的是为了兼容springboot的容器注解的，但是发现行不通，原因在后面讲
                if (!registry.containsBeanDefinition(temp)) {
                    //注册
                    registry.registerBeanDefinition(e.getName(), BeanDefinitionBuilder.rootBeanDefinition(e).getBeanDefinition());
                }
            });
        }
    }

}

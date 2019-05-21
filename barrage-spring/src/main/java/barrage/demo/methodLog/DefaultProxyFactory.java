package barrage.demo.methodLog;

public class DefaultProxyFactory implements ProxyFactoryMeta {

    private AnnotationParserMeta annotationParserMeta;

    public DefaultProxyFactory(AnnotationParserMeta annotationParserMeta) {
        this.annotationParserMeta = annotationParserMeta;
    }

    @Override
    public Object getInstance(Class<?> superClass) {
        return annotationParserMeta.getProxyInstance(superClass);
    }
}

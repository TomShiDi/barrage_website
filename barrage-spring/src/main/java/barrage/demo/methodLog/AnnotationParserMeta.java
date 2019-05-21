package barrage.demo.methodLog;

import java.util.Set;

public interface AnnotationParserMeta {

    /**
     *  do the custom parse method
     */
    void doParse();

    /**
     * get the Class you want to parse
     * @return
     */
    Set<Class<?>> getPointedClasses();

    /**
     * the custom method do before method doParse
     */
    void preDoParse();

    /**
     * the custom method do after method doParse
     */
    void afterDoParse();

    /**
     *  create proxy instance
     */
    Object getProxyInstance(Class<?> clazz);
}

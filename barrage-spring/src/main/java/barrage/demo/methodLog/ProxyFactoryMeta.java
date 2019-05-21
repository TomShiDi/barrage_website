package barrage.demo.methodLog;

public interface ProxyFactoryMeta {

    /**
     * get the proxy instance represent the original class
     * @return
     */
    Object getInstance(Class<?> superClass);
}

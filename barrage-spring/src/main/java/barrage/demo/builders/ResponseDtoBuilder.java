package barrage.demo.builders;

import barrage.demo.dao.CommonDto;
import barrage.demo.enums.BarrageExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/3/25
 **/
public class ResponseDtoBuilder<T extends CommonDto> {

    private T result = null;

    private Class<T> clazz = null;

    private Logger logger = LoggerFactory.getLogger(ResponseDtoBuilder.class);

    public ResponseDtoBuilder(Class<T> clazz) {
        try {
            this.clazz = clazz;
            result = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("创建Builder失败,原因为: {}", e.getMessage());
        }
    }

    public ResponseDtoBuilder code(Integer code) {
        result.setCode(code);
        return this;
    }

    public ResponseDtoBuilder message(String message) {
        result.setMessage(message);
        return this;
    }

    public ResponseDtoBuilder data(Object data) {
        result.setData(data);
        return this;
    }

    public T build() {
        return result;
    }

    public ResponseDtoBuilder enumSet(BarrageExceptionEnum barrageExceptionEnum) {
        result.setCode(barrageExceptionEnum.getCode());
        result.setMessage(barrageExceptionEnum.getMessage());
        return this;
    }

    public ResponseDtoBuilder exBuildMethod(String paramName, Object value, Class<?>... paramTypes)
            throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException {
        Method method = clazz.getMethod(buildMethodName(paramName), paramTypes);
        method.invoke(result, value);
        return this;
    }

    private String buildMethodName(String paramName) {
        return "set" + (paramName.charAt(0) + "").toUpperCase() + paramName.substring(1);
    }
}

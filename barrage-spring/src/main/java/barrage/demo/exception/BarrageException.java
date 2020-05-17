package barrage.demo.exception;

import barrage.demo.enums.AuthEnums;
import barrage.demo.enums.BarrageExceptionEnum;

public class BarrageException extends RuntimeException {

    private Integer code;

//    private String message;

    private Object data;

    public BarrageException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BarrageException(BarrageExceptionEnum barrageExceptionEnum) {

        super(barrageExceptionEnum.getMessage());
        this.code = barrageExceptionEnum.getCode();
    }

    public BarrageException(AuthEnums authEnums) {
        this(authEnums, null);
    }

    public BarrageException(AuthEnums authEnums, Object data) {
        super(authEnums.getMessage());
        this.code = authEnums.getCode();
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

package barrage.demo.exception;

import barrage.demo.enums.BarrageExceptionEnum;

public class BarrageException extends RuntimeException {

    private Integer code;

//    private String message;

    public BarrageException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BarrageException(BarrageExceptionEnum barrageExceptionEnum) {

        super(barrageExceptionEnum.getMessage());
        this.code = barrageExceptionEnum.getCode();
    }
}

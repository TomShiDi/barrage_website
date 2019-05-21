package barrage.demo.enums;

public enum SocketResponseEnum {

    SUCCESS_RECEIVE_RESPONSE(1),
    GET_ONLINE_COUNT(100),
    MESSAGE_RESPONSE(200),
    ;

    private Integer code;

    SocketResponseEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

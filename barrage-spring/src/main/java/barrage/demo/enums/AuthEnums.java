package barrage.demo.enums;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/4/15
 **/
public enum AuthEnums {
    /**
     * 用户账号状态
     */
    STATUS_NO_ACTIVATION(0, "未激活状态"),
    STATUS_ACTIVATION(1, "已激活状态"),
    STATUS_BANNED(2, "被禁用状态"),;

    private Integer code;

    private String message;

    AuthEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

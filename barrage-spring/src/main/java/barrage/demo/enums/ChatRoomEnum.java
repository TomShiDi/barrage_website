package barrage.demo.enums;

public enum ChatRoomEnum {
    /**
     *
     */
    SUCCESS(200),;

    private Integer code;

    ChatRoomEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}

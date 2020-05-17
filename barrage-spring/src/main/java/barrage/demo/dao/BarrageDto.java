package barrage.demo.dao;

import java.util.Map;

/**
 * @Author TomShiDi
 */
public class BarrageDto {

    private String message;

    private Integer code;

    private Map<String, Object> resultData;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getResultData() {
        return resultData;
    }

    public void setResultData(Map<String, Object> resultData) {
        this.resultData = resultData;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

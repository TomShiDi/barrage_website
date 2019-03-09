package barrage.demo.dao;

import java.util.Map;

public class BarrageDao {

    String message;

    Map<String, Object> resultData;

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
}

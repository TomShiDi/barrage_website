package barrage.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpUtil {

    /**
     * 虽然stringBuffer.toString();永远不会为null，但是如果 httpURLConnection.getResponseCode() ！= 200
     * 那result默认值就是null
     * 最终原因是请求频繁被拦截  应该是用了限流熔断框架
     * @param requestUrl
     * @return
     * @throws Exception
     */
    public static String doGet(String requestUrl) throws Exception {
        Assert.hasText(requestUrl, "请求链接不能为空");
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String result = null;
        log.info("当前工作线程是：{}", Thread.currentThread().getName());
        try {
            URL url = new URL(requestUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(80000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer stringBuffer = new StringBuffer();
                String temp;
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuffer.append(temp);
                }
                result = stringBuffer.toString();
            }
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != httpURLConnection) {
                httpURLConnection.disconnect();
            }

        }

        return result;
    }


//    public static String clientHttpGet(String requestUrl) {
//        Assert.isNull(requestUrl,"请求链接不能为空");
//
//    }
}

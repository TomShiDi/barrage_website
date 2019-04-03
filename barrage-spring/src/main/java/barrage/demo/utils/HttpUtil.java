package barrage.demo.utils;

import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static String doGet(String requestUrl) {
        Assert.hasText(requestUrl, "请求链接不能为空");
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String result = null;
        try {
            URL url = new URL(requestUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(6000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer stringBuffer = new StringBuffer();
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuffer.append(temp);
                }
                result = stringBuffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
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
}

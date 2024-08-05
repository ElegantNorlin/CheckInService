package com.wanwan.checkinservice.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


/**
 * @author zhanghao12
 * @time 2023-10-18 17:25:39
 */
@Slf4j
public class PushDeerHttpUtils {
    public static void sendText(String text,String key) throws Exception {
        String url = "https://api2.pushdeer.com/message/push?pushkey=" + key + "&text=" + URLEncoder.encode(text, "UTF-8");
        String response = get(url, "GET", null);
    }

    public static String get(String url, String method, Map<String,Object> headers) throws Exception {
        log.info("\nURL is {}\n请求头：{}",url,(headers == null) ? null : headers.toString());
        URL serverUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) serverUrl.openConnection();
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setInstanceFollowRedirects(false);
        if(headers != null){
            for (Map.Entry<String, Object> header : headers.entrySet()) {
                String key = header.getKey();
                String value = header.getValue().toString();
                httpURLConnection.setRequestProperty(key,value);
            }
        }
        httpURLConnection.connect();
        return getResponseData(httpURLConnection);
    }

    private static String getResponseData(HttpURLConnection httpURLConnection) {
        String string = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        StringBuilder stringBuffer = new StringBuilder();
        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((string = bufferedReader.readLine()) != null) {
                stringBuffer.append(string);
            }
            return stringBuffer.toString();
        }catch (Exception exception){
            log.error(exception.getMessage());
        }finally {
            if (httpURLConnection != null) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception exception) {
                    log.error(exception.getMessage());
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception exception) {
                    log.error(exception.getMessage());
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception exception) {
                    log.error(exception.getMessage());
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception exception) {
                    log.error(exception.getMessage());
                }
            }
        }
        return "请求发生异常，详见日志";
    }
}

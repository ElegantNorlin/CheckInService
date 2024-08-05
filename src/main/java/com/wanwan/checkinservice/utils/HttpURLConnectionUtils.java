package com.wanwan.checkinservice.utils;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.StringJoiner;

@Slf4j
public class HttpURLConnectionUtils {

    /**
     *
     * @author zhanghao
     * @date 2023-12-01 17:24:07
     * @description 基于HttpURLConnection对象封装的get请求
     * @param url
     * @param requestParam
     * @param headers
     * @return String
     * @throws IOException
     */
    public static String get(String url, Map<String,String> requestParam, Map<String,String> headers) throws IOException {
        String requestPath = url;
        StringJoiner stringJoiner = null;
        if(requestParam != null && !requestParam.isEmpty()){
            stringJoiner = new StringJoiner("&","?","");
            for (Map.Entry<String, String> header : headers.entrySet()) {
                String key = header.getKey();
                String value = header.getValue();
                stringJoiner.add(URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8"));
            }
        }
        if(!(stringJoiner == null)){
            requestPath = requestPath + stringJoiner;
        }
        log.info("\n本次get请求：\nURL is {}\n请求头：{}",requestPath,(headers == null) ? null : headers.toString());
        URL requestUrl = new URL(requestPath);
        HttpURLConnection httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        if(headers != null && !(headers.isEmpty())){
            for (Map.Entry<String, String> header : headers.entrySet()) {
                String key = header.getKey();
                String value = header.getValue();
                httpURLConnection.setRequestProperty(key,value);
            }
        }
        String response = getResponse(httpURLConnection);
        httpURLConnection.disconnect();
        return response;
    }

    /**
     *
     * @author zhanghao
     * @date 2023-12-01 17:23:26
     * @description 基于HttpURLConnection对象封装的post请求
     * @param url
     * @param requestParam
     * @param headers
     * @param requestBody
     * @return String
     * @throws IOException
     */
    public static String post(String url, Map<String,String> requestParam, Map<String,String> headers, JSONObject requestBody) throws IOException {
        String requestPath = url;
        StringJoiner stringJoiner = null;
        if(requestParam != null && !requestParam.isEmpty()){
            stringJoiner = new StringJoiner("&","?","");
            for (Map.Entry<String, String> header : headers.entrySet()) {
                String key = header.getKey();
                String value = header.getValue();
                stringJoiner.add(URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8"));
            }
        }
        if(!(stringJoiner == null)){
            requestPath = requestPath + stringJoiner;
        }
        log.info("\n本次post请求：\nURL is {}\n请求头：{}\n请求体：{}",requestPath,(headers == null) ? null : headers.toString(), requestBody);
        URL requestUrl = new URL(requestPath);
        HttpURLConnection httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        if(requestBody != null && !("".equals(requestBody.toString()))){
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(requestBody.toString().getBytes().length));
        }
        if(headers != null && !(headers.isEmpty())){
            for (Map.Entry<String, String> header : headers.entrySet()) {
                String key = header.getKey();
                String value = header.getValue();
                httpURLConnection.setRequestProperty(key,value);
            }
        }
        if(requestBody != null && !("".equals(requestBody.toString()))){
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(requestBody.toString().getBytes());
            outputStream.flush();
            outputStream.close();
        }
        String response = getResponse(httpURLConnection);
        httpURLConnection.disconnect();
        return response;
    }

    /**
     *
     * @author zhanghao
     * @date 2023-12-01 17:22:56
     * @description 获取HttpURLConnection对象的响应信息方法
     * @param httpURLConnection
     * @return String
     * @throws IOException
     */
    private static String getResponse(HttpURLConnection httpURLConnection) throws IOException {
        int statusCode = httpURLConnection.getResponseCode();
        try (
            InputStream inputStream = statusCode == 200 ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
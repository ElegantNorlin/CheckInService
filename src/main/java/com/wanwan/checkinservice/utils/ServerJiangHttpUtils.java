package com.wanwan.checkinservice.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author zhanghao12
 * @time 2023-10-10 15:51:53
 */
@Slf4j
public class ServerJiangHttpUtils {
    public static HashMap<String,Object> scSend(String text, String desp, String key) {
        log.info("\n--------------------------------------------------------------------Server酱请求开始-------------------------------------------------------------------------");
        HashMap<String,Object> hashMap = new HashMap<>();
        try {
            String url = "https://sctapi.ftqq.com/" + key + ".send";
            String postData = "text=" + URLEncoder.encode(text, "UTF-8") + "&desp=" + URLEncoder.encode(desp, "UTF-8") + "&channel=9";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postData);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            log.info("Server酱请求状态码：{}",responseCode);
            if(responseCode < 400){
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                log.info("Server酱响应：{}",response);
                hashMap.put("isSuccess","true");
                hashMap.put("response",response.toString());
                log.info("\n--------------------------------------------------------------------Server酱请求结束-------------------------------------------------------------------------");
                return hashMap;
            }
            hashMap.put("isSuccess","false");
            log.info("\n--------------------------------------------------------------------Server酱请求结束-------------------------------------------------------------------------");
            return hashMap;
        } catch (Exception e) {
            log.error("Server酱请求异常：{}", e);
            hashMap.put("isSuccess","false");
            log.info("\n--------------------------------------------------------------------Server酱请求结束-------------------------------------------------------------------------");
            return hashMap;
        }
    }
}
package com.wanwan.checkinservice.api;

import com.alibaba.fastjson2.JSONObject;
import com.wanwan.checkinservice.utils.HttpURLConnectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class JingDongApi {
    private static String CHECK_IN = "https://api.m.jd.com/client.action?functionId=signBeanAct&body=%7B%22fp%22%3A%22-1%22%2C%22shshshfp%22%3A%22-1%22%2C%22shshshfpa%22%3A%22-1%22%2C%22referUrl%22%3A%22-1%22%2C%22userAgent%22%3A%22-1%22%2C%22jda%22%3A%22-1%22%2C%22rnVersion%22%3A%223.9%22%7D&appid=ld&client=apple&clientVersion=10.0.4&networkType=wifi&osVersion=14.8.1&uuid=3acd1f6361f86fc0a1bc23971b2e7bbe6197afb6&openudid=3acd1f6361f86fc0a1bc23971b2e7bbe6197afb6&jsonp=jsonp_1645885800574_58482";
    private static String BEAN_COUNT = "https://api.m.jd.com/?appid=jd-cphdeveloper-m&functionId=myBean&body=%7B%22tenantCode%22%3A%22jgm%22%2C%22bizModelCode%22%3A6%2C%22bizModeClientType%22%3A%22M%22%2C%22externalLoginType%22%3A1%7D&loginType=2&sceneval=2&g_login_type=1&g_ty=ajax&appCode=ms0ca95114";

    // 签到
    public static JSONObject checkIn(String cookie) throws Exception {
        HashMap<String, String> checkInHeaders = new HashMap<>();
        checkInHeaders.put("Cookie",cookie);
        checkInHeaders.put("Content-Type","application/json");
        checkInHeaders.put("Sec-Ch-Ua-Platform","\"Android\"");
        checkInHeaders.put("Sec-Ch-Ua","\"Google Chrome\";v=\"117\", \"Not;A=Brand\";v=\"8\", \"Chromium\";v=\"117\"");
        checkInHeaders.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36");
        String response = HttpURLConnectionUtils.post(CHECK_IN, null, checkInHeaders, null);
        log.info("get result from JingDong checkIn {}", response);
        return JSONObject.parseObject(response.split("\\(")[1].split("\\)")[0]);
    }

    // 查询京豆资产
    public static JSONObject getCountOfBean(String cookie) throws Exception {
        HashMap<String, String> getCountOfBeanHeaders = new HashMap<>();
        getCountOfBeanHeaders.put("Cookie",cookie);
        getCountOfBeanHeaders.put("Sec-Fetch-Mode","cors");
        getCountOfBeanHeaders.put("Accept","application/json");
        getCountOfBeanHeaders.put("Sec-Fetch-Site","same-site");
        getCountOfBeanHeaders.put("Origin","https://wqs.jd.com");
        getCountOfBeanHeaders.put("Referer","https://wqs.jd.com/");
        getCountOfBeanHeaders.put("Content-Type","application/json");
        getCountOfBeanHeaders.put("Sec-Ch-Ua-Platform","\"Android\"");
        getCountOfBeanHeaders.put("Access-Control-Allow-Origin","https://wqs.jd.com");
        String response = HttpURLConnectionUtils.get(BEAN_COUNT, null, getCountOfBeanHeaders);
        log.info("get result from JingDong getCountOfBean {}", response);
        return JSONObject.parseObject(response);
    }
}

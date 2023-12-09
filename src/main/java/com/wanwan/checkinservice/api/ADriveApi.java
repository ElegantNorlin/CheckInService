package com.wanwan.checkinservice.api;

import com.alibaba.fastjson2.JSONObject;
import com.wanwan.checkinservice.utils.HttpURLConnectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;

@Slf4j
public class ADriveApi {
    private static final String CHECK_IN = "https://member.aliyundrive.com/v1/activity/sign_in_list";
    private static final String GET_AWARD = "https://member.aliyundrive.com/v1/activity/sign_in_reward?_rx-s=mobile";

    // 阿里网盘签到
    public static JSONObject checkIn(String token) throws Exception {
        HashMap<String, String> checkInHeaders = new HashMap<>();
        checkInHeaders.put("Authorization","Bearer " + token);
        JSONObject jsonObject = JSONObject.parseObject("{\"_rx-s\":\"mobile\"}");
        String response = HttpURLConnectionUtils.post(CHECK_IN, null, checkInHeaders, jsonObject);
        log.info("get result from aDrive checkIn {}", response);
        return JSONObject.parseObject(response);
    }

    // 领取签到奖励
    public static JSONObject getAward(String token,Integer day) throws Exception {
        HashMap<String, String> getCountOfBeanHeaders = new HashMap<>();
        getCountOfBeanHeaders.put("Authorization","Bearer " + token);
        JSONObject jsonObject = JSONObject.parseObject("{\"signInDay\": " + day + "}");
        String response = HttpURLConnectionUtils.post(GET_AWARD, null, getCountOfBeanHeaders,jsonObject);
        log.info("get result from aDrive getCountOfBean {}", response);
        return JSONObject.parseObject(response);
    }
}

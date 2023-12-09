package com.wanwan.checkinservice.api;

import com.alibaba.fastjson2.JSONObject;
import com.wanwan.checkinservice.utils.HttpURLConnectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author zhanghao12
 * @time 2023-10-31 14:14:40
 */
@Slf4j
public class JinShanApi {
    public static final String DAILY_SENTENCE = "https://open.iciba.com/dsapi/";

    // 金山每日一句
    public static JSONObject dailySentence() throws Exception {
        String response = HttpURLConnectionUtils.post(DAILY_SENTENCE, null, null, null);
        log.info("get result from jinshan dailySentence {}", response);
        return JSONObject.parseObject(response);
    }
}

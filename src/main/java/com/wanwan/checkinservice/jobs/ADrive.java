package com.wanwan.checkinservice.jobs;

import com.alibaba.fastjson2.JSONObject;
import com.wanwan.checkinservice.api.ADriveApi;
import com.wanwan.checkinservice.api.JingDongApi;
import com.wanwan.checkinservice.utils.PushDeerHttpUtils;
import com.wanwan.checkinservice.utils.ServerJiangHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ADrive {

    @Value("${aDrive}")
    String token;

    @Value("${push_deer_key}")
    private String push_deer_key;

    @Value("${server_jiang_key}")
    private String server_jiang_key;

    @Scheduled(cron = "${aDrive_cron_expression}")
    public void autoTask() throws Exception {
        log.info("\n--------------------------------------------------------------------京东自动化任务开始-------------------------------------------------------------------------");
        // 签到
        JSONObject checkInJsonObject = ADriveApi.checkIn(token);
        log.info("阿里网盘签到接口响应信息为 {}",checkInJsonObject);
        // 查询当前京东总数
        String day = checkInJsonObject.getJSONObject("result").get("signInCount").toString();
        JSONObject getAwardJsonObject = ADriveApi.getAward(token,Integer.parseInt(day));
        log.info("阿里网盘领取签到奖励接口响应信息为 {}",getAwardJsonObject);
        String result = checkInJsonObject.toString() + "\n" + getAwardJsonObject.toString();
        if(server_jiang_key != null && !"".equals(server_jiang_key)){
            ServerJiangHttpUtils.scSend("阿里网盘签到助手",result,server_jiang_key);
        }
//        if(push_deer_key != null && !"".equals(push_deer_key)){
//            PushDeerHttpUtils.sendText(result,push_deer_key);
//        }
        log.info("\n--------------------------------------------------------------------京东自动化任务结束-------------------------------------------------------------------------");
    }
}
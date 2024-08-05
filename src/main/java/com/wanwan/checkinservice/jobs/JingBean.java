package com.wanwan.checkinservice.jobs;

import com.alibaba.fastjson2.JSONObject;
import com.wanwan.checkinservice.api.JingDongApi;
import com.wanwan.checkinservice.utils.PushDeerHttpUtils;
import com.wanwan.checkinservice.utils.ServerJiangHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JingBean {

    @Value("${jingdong_cookie}")
    String jingdong_cookie;

    @Value("${push_deer_key}")
    private String push_deer_key;

    @Value("${jingdong_enabled}")
    private String jingdong_enabled;

    @Value("${server_jiang_key}")
    private String server_jiang_key;

    private boolean isInvalidOfCookie = false;

    @Scheduled(cron = "${jingdong_corn_expression}")
    public void getJingBean() throws Exception {
        if("false".equals(jingdong_enabled)){
            return;
        }
        if(isInvalidOfCookie){
            isInvalidOfCookie = false;
            if(server_jiang_key != null && !server_jiang_key.isEmpty()){
                ServerJiangHttpUtils.scSend("京东签到助手","jingdong_cookie失效！请更新有效cookie并手动执行脚本！",server_jiang_key);
            }
            if(push_deer_key != null && !push_deer_key.isEmpty()){
                PushDeerHttpUtils.sendText("jingdong_cookie失效！请更新有效cookie并手动执行脚本！",push_deer_key);
            }
            return;
        }
        log.info("\n--------------------------------------------------------------------京东自动化任务开始-------------------------------------------------------------------------");
        // 签到
        JSONObject checkInJsonObject = JingDongApi.checkIn(jingdong_cookie);
        log.info("京东签到接口响应信息为 {}",checkInJsonObject);
        // 查询当前京东总数
        JSONObject countOfBeanJsonObject = JingDongApi.getCountOfBean(jingdong_cookie);
        log.info("京东查询京豆总数接口响应信息为 {}",countOfBeanJsonObject);
        // 处理响应结果
        String resultData = "您已连续签到" + checkInJsonObject.getJSONObject("data").get("continuousDays").toString() + "天\n\n" +
                ((checkInJsonObject.getJSONObject("data").getJSONObject("dailyAward") == null) ? checkInJsonObject.getJSONObject("data").getJSONObject("continuityAward").get("title").toString() : checkInJsonObject.getJSONObject("data").getJSONObject("dailyAward").get("title").toString()) +
                "获得奖励：" + ((checkInJsonObject.getJSONObject("data").getJSONObject("dailyAward") == null) ? checkInJsonObject.getJSONObject("data").getJSONObject("continuityAward").getJSONObject("beanAward").get("beanCount").toString() : checkInJsonObject.getJSONObject("data").getJSONObject("dailyAward").getJSONObject("beanAward").get("beanCount").toString()) + "京豆\n\n您的京豆总资产为：" +
                countOfBeanJsonObject.get("beanNum").toString() + "京豆";
        // 发送本次任务的ServerJiang微信服务号通知
        if(server_jiang_key != null && !server_jiang_key.isEmpty()){
            ServerJiangHttpUtils.scSend("京东签到助手",resultData,server_jiang_key);
        }
        if(push_deer_key != null && !push_deer_key.isEmpty()){
            PushDeerHttpUtils.sendText(resultData,push_deer_key);
        }
        log.info("\n--------------------------------------------------------------------京东自动化任务结束-------------------------------------------------------------------------");
    }

    /**
     *
     * @author wanwan
     * @time 2023-11-03 14:35:08
     * @description 校验jingdong_cookie是否失效
     * @param
     * @return true过期   false没过期
     * @throws Exception
     */
    @Scheduled(cron = "0 0 7,12,21 * * ?")
    public void isInvalidOfCookie() throws Exception {
        JSONObject getCountOfBeanJsonObject = JingDongApi.getCountOfBean(jingdong_cookie);
        log.info("本次jingdong_cookie校验响应信息：{}", getCountOfBeanJsonObject);
        if(getCountOfBeanJsonObject.get("code") == null){
            isInvalidOfCookie = false;
        }else {
            log.info("jingdong_cookie过期，请重新获取");
            isInvalidOfCookie = true;
        }
    }
}
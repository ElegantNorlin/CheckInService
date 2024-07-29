package com.wanwan.checkinservice.jobs;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanwan.checkinservice.api.JinShanApi;
import com.wanwan.checkinservice.api.JueJinApi;
import com.wanwan.checkinservice.utils.PushDeerHttpUtils;
import com.wanwan.checkinservice.utils.ServerJiangHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JueJin {

    @Value("${juejin_enable}")
    private String juejin_enable;

    @Value("${juejin_cookie}")
    private String juejin_cookie;

    @Value("${push_deer_key}")
    private String push_deer_key;

    @Value("${server_jiang_key}")
    private String server_jiang_key;

    @Value("${juejin_growth_enabled}")
    private String juejin_growth_enabled;

    private boolean isInvalidOfCookie = false;

    /**
     * @return
     * @author wanwan
     * @time 2023-10-10 11:20:11
     * @description 每日自动执行方法
     */
    @Scheduled(cron = "${juejin_cron_expression}")
    public void juejinCheckInAndDraw() throws Exception {
        if ("false".equals(juejin_enable)) {
            return;
        }
        if (isInvalidOfCookie) {
            if (server_jiang_key != null && !"".equals(server_jiang_key)) {
                ServerJiangHttpUtils.scSend("掘金等级成长助手", "juejin_cookie失效！请更新有效cookie并手动执行脚本！", server_jiang_key);
            }
            if (push_deer_key != null && !"".equals(push_deer_key)) {
                PushDeerHttpUtils.sendText("juejin_cookie失效！请更新有效cookie并手动执行脚本！", push_deer_key);
            }
        }
        log.info("\n--------------------------------------------------------------------掘金自动化任务开始-------------------------------------------------------------------------");
        String result = "";
        HashMap<String, Object> resultHashMap = new HashMap<>();
        JSONObject todayStatusJSONObject = JueJinApi.getStateOfCheckIn(juejin_cookie);
        JSONObject countOfCheckInJSONObject = JueJinApi.getDayCountOfCheckIn(juejin_cookie);
        if (!"0".equals(todayStatusJSONObject.get("err_no").toString())) {
            resultHashMap.put("cookie", "Cookie失效！请更新有效Cookie");
        } else {
            if ("true".equals(todayStatusJSONObject.get("data").toString())) {
                resultHashMap.put("checkIn", "今日已签到！");
            } else {
                JSONObject checkInHashJSONObject = JueJinApi.checkIn(juejin_cookie);
                if ("0".equals(checkInHashJSONObject.get("err_no").toString())) {
                    resultHashMap.put("checkIn", "今日签到成功！获得" + checkInHashJSONObject.getJSONObject("data").get("incr_point").toString() + "矿石！");
                } else {
                    resultHashMap.put("checkIn", "今日签到失败！");
                }
            }
            JSONObject lotteryConfigGetJSONObject = JueJinApi.getCountOfFreeDraw(juejin_cookie);
            if ("0".equals(lotteryConfigGetJSONObject.getJSONObject("data").get("free_count").toString())) {
                resultHashMap.put("draw", "今日已免费抽奖！");
            } else {
                JSONObject drawHashJSONObject = JueJinApi.draw(juejin_cookie);
                if ("0".equals(drawHashJSONObject.get("err_no").toString())) {
                    resultHashMap.put("draw", "免费抽奖获得：" + drawHashJSONObject.getJSONObject("data").get("lottery_name").toString());
                } else {
                    resultHashMap.put("draw", "抽奖免费失败！");
                }
            }
        }
        JSONObject curPointJSONObject = JueJinApi.getCountOfOre(juejin_cookie);
        if (resultHashMap.get("cookie") != null) {
            ServerJiangHttpUtils.scSend("掘金签到助手", resultHashMap.get("cookie").toString(), server_jiang_key);
            result = resultHashMap.get("cookie").toString();
        } else {
            resultHashMap.put("curPointHashMap", "您的矿石总资产为：" + curPointJSONObject.get("data").toString() + "矿石");
            resultHashMap.put("countOfCheckInHashMap", "您已连续签到" + countOfCheckInJSONObject.getJSONObject("data").get("cont_count").toString() + "天！");
            result = resultHashMap.get("curPointHashMap").toString() + "\n\n" + resultHashMap.get("countOfCheckInHashMap").toString() + "\n\n" + resultHashMap.get("checkIn").toString() + "\n\n" + resultHashMap.get("draw").toString();
            // 发送本次任务的ServerJiang微信服务号通知
            if (server_jiang_key != null && !"".equals(server_jiang_key)) {
                ServerJiangHttpUtils.scSend("掘金签到助手", result, server_jiang_key);
            }

            if (push_deer_key != null && !"".equals(push_deer_key)) {
                PushDeerHttpUtils.sendText(result, push_deer_key);
            }
        }
        // 通知内容记录到日志
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        log.info("\nServer酱通知时间：" + sdf.format(date) + "\nServer酱通知内容：" + result);
        log.info("\n--------------------------------------------------------------------掘金自动化任务结束-------------------------------------------------------------------------");
    }

    /**
     * @return
     * @throws Exception
     * @author wanwan
     * @time 2023-10-22 11:20:11
     * @description 掘金等级成长任务
     */
    @Scheduled(cron = "${juejin_growth_expression}")
    public void jueJinGrowth() throws Exception {
        if ("false".equals(juejin_growth_enabled)) {
            return;
        }
        if (isInvalidOfCookie) {
            if (server_jiang_key != null && !"".equals(server_jiang_key)) {
                ServerJiangHttpUtils.scSend("掘金等级成长助手", "juejin_cookie失效！请更新有效cookie并手动执行脚本！", server_jiang_key);
            }
            if (push_deer_key != null && !"".equals(push_deer_key)) {
                PushDeerHttpUtils.sendText("juejin_cookie失效！请更新有效cookie并手动执行脚本！", push_deer_key);
            }
            return;
        }
        log.info("\n--------------------------------------------------------------------掘金等级成长任务开始-------------------------------------------------------------------------");
        // 沸点相关任务
        // 调用金山每日一句接口
        JSONObject dailySentenceJsonObject = JinShanApi.dailySentence();
        // 发布第一条沸点
        JSONObject firstBoilingPointJsonObject = JueJinApi.publishBoilingPoint(juejin_cookie, JSONObject.parseObject("{\"content\":\"" + dailySentenceJsonObject.get("content").toString().replace(String.valueOf('\"'), "'") + "\",\"sync_to_org\":false}"));
        // 点赞第一条沸点
        JSONObject firstUpvoteBoilingPointJsonObject = JueJinApi.upvoteBoilingPoint(juejin_cookie, JSONObject.parseObject("{\"item_id\":\"" + firstBoilingPointJsonObject.getJSONObject("data").get("msg_id").toString() + "\",\"item_type\":4,\"client_type\":2608}"));
        // 第一条沸点评论三次
        for (int i = 0; i < 3; i++) {
            JueJinApi.commentBoilingPoint(juejin_cookie, JSONObject.parseObject("{\"client_type\":2608,\"item_id\":\"" + firstBoilingPointJsonObject.getJSONObject("data").get("msg_id").toString() + "\",\"item_type\":4,\"comment_content\":\"[发呆][发呆][发呆]好好好，你这么整是吧！\",\"comment_pics\":[]}"));
        }
        // 线程休眠一小会，否则第二条沸点会由于发布过于频繁导致失败
        Thread.sleep(6000);
        // 发布第二条沸点
        JSONObject secondBoilingPointJsonObject = JueJinApi.publishBoilingPoint(juejin_cookie, JSONObject.parseObject("{\"content\":\"" + dailySentenceJsonObject.get("note").toString().replace(String.valueOf('\"'), "'") + "\",\"sync_to_org\":false}"));
        JSONObject secondUpvoteBoilingPointJsonObject = JueJinApi.upvoteBoilingPoint(juejin_cookie, JSONObject.parseObject("{\"item_id\":\"" + secondBoilingPointJsonObject.getJSONObject("data").get("msg_id").toString() + "\",\"item_type\":4,\"client_type\":2608}"));
        // 第二条沸点评论三次
        for (int i = 0; i < 3; i++) {
            JueJinApi.commentBoilingPoint(juejin_cookie, JSONObject.parseObject("{\"client_type\":2608,\"item_id\":\"" + secondBoilingPointJsonObject.getJSONObject("data").get("msg_id").toString() + "\",\"item_type\":4,\"comment_content\":\"[发呆][发呆][发呆]好好好，你这么整是吧！\",\"comment_pics\":[]}"));
        }

        // 文章相关任务
        JSONObject articleListJSONObject = JueJinApi.getArticleList(juejin_cookie);
        JSONArray articleListDataJsonArray = articleListJSONObject.getJSONArray("data");
        // 两篇文章 点赞 + 评论
        for (int i = 0; i < 2; i++) {
            String content_id = articleListDataJsonArray.getJSONObject(i).getJSONObject("content").get("content_id").toString();
            JueJinApi.upvoteArticle(juejin_cookie, JSONObject.parseObject("{\"item_id\":\"" + content_id + "\",\"item_type\":2,\"client_type\":2608}"));
            JueJinApi.articleComment(juejin_cookie, JSONObject.parseObject("{\"client_type\":2608,\"item_id\":\"" + content_id + "\",\"item_type\":2,\"comment_content\":\"大佬666哇[赞][赞][赞]\",\"comment_pics\":[]}"));
        }

        // 关注掘友任务
        JSONObject rankJSONObject = JueJinApi.rank(JSONObject.parseObject("{\"item_rank_type\":1,\"item_sub_rank_type\":\"6809637769959178254\"}"));
        JSONArray rankJsonArray = rankJSONObject.getJSONObject("data").getJSONArray("user_rank_list");
        for (int i = 0; i < 2; i++) {
            JueJinApi.subscribe(juejin_cookie, JSONObject.parseObject("{\"id\":\"" + rankJsonArray.getJSONObject(i).getJSONObject("user_info").get("user_id").toString() + "\",\"type\":1}"));
            Thread.sleep(6000);
            JueJinApi.unSubscribe(juejin_cookie, JSONObject.parseObject("{\"id\":\"" + rankJsonArray.getJSONObject(i).getJSONObject("user_info").get("user_id").toString() + "\",\"type\":1}"));
        }
        // 查询今天累计获得掘金分
        JSONObject taskListJsonObject = JueJinApi.taskList(juejin_cookie, JSONObject.parseObject("{\"growth_type\":1}"));
        // 查询掘友分并拼接消息
        StringJoiner stringJoiner = new StringJoiner("\n\n");
        // 获取当前等级信息
        JSONObject progressJSONObject = JueJinApi.progress(juejin_cookie, JSONObject.parseObject("{\"growth_type\":1}"));
        // 获取当前等级最大掘友分！
        JSONArray jsonArray = progressJSONObject.getJSONObject("data").getJSONArray("level_spec");
        List<JSONObject> levelSpecList = jsonArray.stream().map(object -> (JSONObject) object).collect(Collectors.toList());
        levelSpecList.stream().forEach(levelSpec -> {
            if (progressJSONObject.getJSONObject("data").get("current_level").toString().equals(levelSpec.get("level").toString())) {
                stringJoiner.add("距离升级您还差" + (Double.parseDouble(levelSpec.get("max_score").toString()) - Double.parseDouble(progressJSONObject.getJSONObject("data").get("current_score").toString())) + "掘友分！");
            }
        });
        stringJoiner.add("您的掘友等级为：" + progressJSONObject.getJSONObject("data").get("current_level").toString() + "级掘友");

        stringJoiner.add("您的总掘友分为：" + progressJSONObject.getJSONObject("data").get("current_score").toString() + "分");
        stringJoiner.add("今日累计获得" + taskListJsonObject.getJSONObject("data").get("today_jscore").toString() + "掘友分！");
        stringJoiner.add("掘金后台API接口查询明细：");
        taskListJsonObject.getJSONObject("data").getJSONObject("growth_tasks").getJSONArray("1").forEach(object -> {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            if (!"0".equals(jsonObject.get("done").toString())) {
                double score = Double.parseDouble(jsonObject.get("score").toString());
                double done = Double.parseDouble(jsonObject.get("done").toString());
                stringJoiner.add(jsonObject.get("title").toString() + "任务获得" + score * done + "掘友分");
            }
        });
        taskListJsonObject.getJSONObject("data").getJSONObject("growth_tasks").getJSONArray("3").forEach(object -> {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            if (!"0".equals(jsonObject.get("done").toString())) {
                double score = Double.parseDouble(jsonObject.get("score").toString());
                double done = Double.parseDouble(jsonObject.get("done").toString());
                stringJoiner.add(jsonObject.get("title").toString() + "任务获得" + score * done + "掘友分");
            }
        });
        taskListJsonObject.getJSONObject("data").getJSONObject("growth_tasks").getJSONArray("4").forEach(object -> {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            if (!"0".equals(jsonObject.get("done").toString())) {
                double score = Double.parseDouble(jsonObject.get("score").toString());
                double done = Double.parseDouble(jsonObject.get("done").toString());
                stringJoiner.add(jsonObject.get("title").toString() + "任务获得" + score * done + "掘友分");
            }
        });
        taskListJsonObject.getJSONObject("data").getJSONObject("growth_tasks").getJSONArray("5").forEach(object -> {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
            if (!"0".equals(jsonObject.get("done").toString())) {
                double score = Double.parseDouble(jsonObject.get("score").toString());
                double done = Double.parseDouble(jsonObject.get("done").toString());
                stringJoiner.add(jsonObject.get("title").toString() + "任务获得" + score * done + "掘友分");
            }
        });
        String result = stringJoiner.toString();
        if (server_jiang_key != null && !"".equals(server_jiang_key)) {
            ServerJiangHttpUtils.scSend("掘金等级成长助手", result, server_jiang_key);
        }
        if (push_deer_key != null && !"".equals(push_deer_key)) {
            PushDeerHttpUtils.sendText(result, push_deer_key);
        }
        log.info("\n--------------------------------------------------------------------掘金等级成长任务结束-------------------------------------------------------------------------");
    }

    /**
     * @param
     * @return
     * @throws Exception
     * @author wanwan
     * @time 2023-11-03 14:35:08
     * @description 校验juejin_cookie是否失效
     */
    @Scheduled(cron = "0 0 7,12,21 * * ?")
    public void isInvalidOfCookie() throws Exception {
        JSONObject taskListJsonObject = JueJinApi.taskList(juejin_cookie, JSONObject.parseObject("{\"growth_type\":1}"));
        log.info("本次juejin_cookie校验响应信息：" + taskListJsonObject);
        if (taskListJsonObject.getJSONObject("data").containsKey("err_no") && "403".equals(taskListJsonObject.getJSONObject("data").get("err_no").toString())) {
            log.info("juejin_cookie已失效！");
            isInvalidOfCookie = true;

        } else {
            log.info("juejin_cookie仍然有效！");
            isInvalidOfCookie = false;
        }
    }
}
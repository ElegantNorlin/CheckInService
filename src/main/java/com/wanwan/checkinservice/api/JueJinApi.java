package com.wanwan.checkinservice.api;

import com.alibaba.fastjson2.JSONObject;
import com.wanwan.checkinservice.utils.HttpURLConnectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author zhanghao12
 * @time 2023-10-27 10:37:28
 */
@Slf4j
public class JueJinApi {
    // 签到
    public static final String CHECK_IN = "https://api.juejin.cn/growth_api/v1/check_in";
    // 抽奖
    public static final String DRAW = "https://api.juejin.cn/growth_api/v1/lottery/draw";
    // 获取矿石总数
    public static final String GET_CUR_POINT = "https://api.juejin.cn/growth_api/v1/get_cur_point";
    // 查询今天是否已签到
    public static final String GET_TODAY_STATUS = "https://api.juejin.cn/growth_api/v1/get_today_status";
    // 查询连续签到天数
    public static final String GET_COUNTS = "https://api.juejin.cn/growth_api/v1/get_counts";
    // 查询免费抽奖次数
    public static final String LOTTERY_CONFIG_GET = "https://api.juejin.cn/growth_api/v1/lottery_config/get";
    // 沸点list接口
    public static final String RECOMMEND = "https://api.juejin.cn/recommend_api/v1/short_msg/recommend";
    // 沸点点赞接口
    public static final String SAVE = "https://api.juejin.cn/interact_api/v1/digg/save";
    // 发布沸点接口
    public static final String PUBLISH = "https://api.juejin.cn/content_api/v1/short_msg/publish";
    //
    public static final String COMMENT_PUBLISH = "https://api.juejin.cn/interact_api/v1/comment/publish";
    // 文章列表
    public static final String ARTICLE_RANK = "https://api.juejin.cn/content_api/v1/content/article_rank?category_id=1&type=hot&count=50&from=0";
    // 文章点赞
    public static final String ARTICLE_SAVE = "https://api.juejin.cn/interact_api/v1/digg/save";
    // 评论文章
    public static final String ARTICLE_COMMENT = "https://api.juejin.cn/interact_api/v1/comment/publish";
    // 查询今日获得掘友分
    public static final String TASK_LIST = "https://api.juejin.cn/growth_api/v1/user_growth/task_list";
    // 掘友列表
    public static final String RANK = "https://api.juejin.cn/user_api/v1/quality_user/rank";
    // 关注掘友
    public static final String DO = "https://api.juejin.cn/interact_api/v1/follow/do";
    // 取关掘友
    public static final String UNDO = "https://api.juejin.cn/interact_api/v1/follow/undo";
    // 查询总掘友分
    public static final String PROGRESS = "https://api.juejin.cn/growth_api/v1/user_growth/progress";

    // 签到
    public static JSONObject checkIn(String cookie) throws Exception {
        HashMap<String, String> checkInHeaders = new HashMap<>();
        checkInHeaders.put("Cookie",cookie);
        String response = HttpURLConnectionUtils.post(CHECK_IN, null, checkInHeaders, null);
        log.info("get result from juejin checkIn {}", response);
        return JSONObject.parseObject(response);
    }

    // 抽奖
    public static JSONObject draw(String cookie) throws Exception {
        HashMap<String, String> drawHeaders = new HashMap<>();
        drawHeaders.put("Cookie",cookie);
        String response = HttpURLConnectionUtils.post(DRAW, null, drawHeaders, null);
        log.info("get result from juejin draw {}", response);
        return JSONObject.parseObject(response);
    }

    // 获取矿石总数
    public static JSONObject getCountOfOre(String cookie) throws Exception {
        HashMap<String, String> getCountOfOreHeaders = new HashMap<>();
        getCountOfOreHeaders.put("Cookie",cookie);
        String response = HttpURLConnectionUtils.get(GET_CUR_POINT, null, getCountOfOreHeaders);
        log.info("get result from juejin getCountOfOre {}", response);
        return JSONObject.parseObject(response);
    }

    // 查询签到状态
    public static JSONObject getStateOfCheckIn(String cookie) throws Exception {
        HashMap<String, String> getStateOfCheckInHeaders = new HashMap<>();
        getStateOfCheckInHeaders.put("Cookie", cookie);
        String response = HttpURLConnectionUtils.get(GET_TODAY_STATUS, null, getStateOfCheckInHeaders);
        log.info("get result from juejin getStateOfCheckIn {}", response);
        return JSONObject.parseObject(response);
    }

    // 查询连续签到天数和总签到天数
    public static JSONObject getDayCountOfCheckIn(String cookie) throws Exception {
        HashMap<String, String> getDayCountOfCheckInHeaders = new HashMap<>();
        getDayCountOfCheckInHeaders.put("Cookie", cookie);
        String response = HttpURLConnectionUtils.get(GET_COUNTS, null, getDayCountOfCheckInHeaders);
        log.info("get result from juejin getDayCountOfCheckIn {}", response);
        return JSONObject.parseObject(response);
    }

    // 查询免费抽奖次数
    public static JSONObject getCountOfFreeDraw(String cookie) throws Exception {
        HashMap<String, String> getCountOfFreeDrawHeaders = new HashMap<>();
        getCountOfFreeDrawHeaders.put("Cookie", cookie);
        String response = HttpURLConnectionUtils.get(LOTTERY_CONFIG_GET, null, getCountOfFreeDrawHeaders);
        log.info("get result from juejin getCountOfFreeDraw {}", response);
        return JSONObject.parseObject(response);
    }

    // 查询沸点列表
    public static JSONObject getBoilingPointList(JSONObject bodyParam) throws Exception {
        String response = HttpURLConnectionUtils.post(RECOMMEND, null, null,bodyParam);
        log.info("get result from juejin getBoilingPointList {}", response);
        return JSONObject.parseObject(response);
    }

    // 点赞沸点
    public static JSONObject upvoteBoilingPoint(String cookie,JSONObject bodyParam) throws Exception {
        HashMap<String, String> upvoteBoilingPointHeaders = new HashMap<>();
        upvoteBoilingPointHeaders.put("Cookie", cookie);
        String response = HttpURLConnectionUtils.post(SAVE, null, upvoteBoilingPointHeaders,bodyParam);
        log.info("get result from juejin getBoilingPointList {}", response);
        return JSONObject.parseObject(response);
    }

    // 发布沸点
    public static JSONObject publishBoilingPoint(String cookie,JSONObject bodyParam) throws Exception {
        HashMap<String, String> publishBoilingPointHeaders = new HashMap<>();
        publishBoilingPointHeaders.put("Cookie", cookie);
        String response = HttpURLConnectionUtils.post(PUBLISH, null, publishBoilingPointHeaders,bodyParam);
        log.info("get result from juejin publishBoilingPoint {}", response);
        return JSONObject.parseObject(response);
    }

    // 评论沸点 请求体示例 : {"client_type":2608,"item_id":"7294198391575904307","item_type":4,"comment_content":"6","comment_pics":[]}
    public static JSONObject commentBoilingPoint(String cookie,JSONObject bodyParam) throws Exception {
        HashMap<String, String> commentBoilingPointHeaders = new HashMap<>();
        commentBoilingPointHeaders.put("Cookie", cookie);
        String response = HttpURLConnectionUtils.post(COMMENT_PUBLISH, null, commentBoilingPointHeaders,bodyParam);
        log.info("get result from juejin commentBoilingPoint {}", response);
        return JSONObject.parseObject(response);
    }

    // 查询文章列表
    public static JSONObject getArticleList(String cookie) throws Exception {
        String response = HttpURLConnectionUtils.get(ARTICLE_RANK, null, null);
        log.info("get result from juejin getArticleList {}", response);
        return JSONObject.parseObject(response);
    }

    // 点赞文章 请求体示例 : {"item_id":"7293356159939313703","item_type":2,"client_type":2608}
    public static JSONObject upvoteArticle(String cookie,JSONObject bodyParam) throws Exception {
        HashMap<String, String> articleSaveHeaders = new HashMap<>();
        articleSaveHeaders.put("Cookie", cookie);
        String response = HttpURLConnectionUtils.post(ARTICLE_SAVE, null, articleSaveHeaders,bodyParam);
        log.info("get result from juejin upvoteArticle {}", response);
        return JSONObject.parseObject(response);
    }

    // 评论文章 请求体示例 : {"client_type":2608,"item_id":"7293356159939313703","item_type":2,"comment_content":"666","comment_pics":[]}
    public static JSONObject articleComment(String cookie,JSONObject bodyParam) throws Exception {
        HashMap<String, String> articleCommentHeaders = new HashMap<>();
        articleCommentHeaders.put("Cookie", cookie);
        String response = HttpURLConnectionUtils.post(ARTICLE_COMMENT, null, articleCommentHeaders,bodyParam);
        log.info("get result from juejin articleComment {}", response);
        return JSONObject.parseObject(response);
    }

    // 查询掘友分 请求体示例 : {"growth_type":1}
    public static JSONObject taskList(String cookie,JSONObject bodyParam) throws Exception {
        HashMap<String, String> taskListHeaders = new HashMap<>();
        taskListHeaders.put("Cookie", cookie);
        String response = HttpURLConnectionUtils.post(TASK_LIST, null, taskListHeaders,bodyParam);
        log.info("get result from juejin taskList {}", response);
        return JSONObject.parseObject(response);
    }

    // 掘友列表 请求体示例 : {"item_rank_type":1,"item_sub_rank_type":"6809637769959178254"}
    public static JSONObject rank(JSONObject bodyParam) throws Exception {
        String response = HttpURLConnectionUtils.post(RANK, null, null,bodyParam);
        log.info("get result from juejin rank {}", response);
        return JSONObject.parseObject(response);
    }

    // 关注掘友 请求体示例 : {"id":"1732486057428952","type":1}
    public static JSONObject subscribe(String cookie,JSONObject bodyParam) throws Exception {
        HashMap<String, String> subscribeHeaders = new HashMap<>();
        subscribeHeaders.put("Cookie", cookie);
        String response = HttpURLConnectionUtils.post(DO, null, subscribeHeaders,bodyParam);
        log.info("get result from juejin subscribe {}", response);
        return JSONObject.parseObject(response);
    }

    // 取关掘友 请求体示例 : {"id":"1732486057428952","type":1}
    public static JSONObject unSubscribe(String cookie,JSONObject bodyParam) throws Exception {
        HashMap<String, String> unSubscribeHeaders = new HashMap<>();
        unSubscribeHeaders.put("Cookie", cookie);
        String response = HttpURLConnectionUtils.post(UNDO, null, unSubscribeHeaders,bodyParam);
        log.info("get result from juejin unSubscribe {}", response);
        return JSONObject.parseObject(response);
    }

    // 查询总掘友分 请求体示例 : {"growth_type":1}
    public static JSONObject progress(String cookie,JSONObject bodyParam) throws Exception {
        HashMap<String, String> progressHeaders = new HashMap<>();
        progressHeaders.put("Cookie", cookie);
        String response = HttpURLConnectionUtils.post(PROGRESS, null, progressHeaders,bodyParam);
        log.info("get result from juejin progress {}", response);
        return JSONObject.parseObject(response);
    }
}
<img src="https://github.com/ElegantNorlin/CheckInService/blob/main/img/Snipaste_2024-07-29_20-07-43.png" width="90%">
# ‍💻CheckInService

基于SpringBoot开发的京东、掘金、阿里网盘定时签到服务（薅羊毛）

### 1 🎉签到服务实现功能

*   掘金自动化任务
    *   签到✔
    *   每日免费抽奖✔
    *   等级成长
        * 掘友等级✔
        * 掘友总分✔
        * 当日获得掘友分✔
        * 发布沸点✔
        * 点赞沸点✔
        * 评论沸点✔
        * 文章发布（由于文章发布会被审核，有封号的风险，所以暂时不打算实现）
        * 文章评论✔
        * 文章点赞✔
*   京东自动化任务（关于狗东：目前只找到了签到接口，估计其他接口狗东都没开放给网页版。因此，该服务目前只支持狗东自动签到，如果大家有能用的接口，欢迎提供！）
    *   签到✔
    *   京豆资产查询✔
*   阿里网盘 (暂时有点小问题待修复)
    * 自动签到✔
    * 自动领取签到奖励✔
    * server酱通知✔
    * pushdeer暂未开发通知（Todo）
*   docker compose一键部署✔
*   掘金、京东Cookie失效-Server酱通知✔
*   自动化任务结果-Server酱微信服务号通知✔
*   PushDeer消息通知（iOS and Android APP通知）✔

### 2 🔊推送消息截图
#### 2.1 [Server酱推送](https://sct.ftqq.com/)
京东签到
<br>
<img src="https://github.com/ElegantNorlin/CheckInService/blob/main/img/IMG_2844.PNG" width="50%">
<br>
掘金签到、抽奖
<br>
<img src="https://github.com/ElegantNorlin/CheckInService/blob/main/img/IMG_2845.PNG" width="50%">
<br>
掘金等级成长
<br>
<img src="https://github.com/ElegantNorlin/CheckInService/blob/main/img/IMG_2846.PNG" width="50%">
<br>
#### 2.2 [PushDeer推送](https://www.pushdeer.com/)
<img src="https://github.com/ElegantNorlin/CheckInService/blob/main/img/IMG_2847.PNG" width="50%">
<img src="https://github.com/ElegantNorlin/CheckInService/blob/main/img/IMG_2848.PNG" width="50%">
<img src="https://github.com/ElegantNorlin/CheckInService/blob/main/img/IMG_2849.PNG" width="50%">

### 3 😋食用方法(docker compose一键部署)

1. 下载项目[压缩包](https://github.com/ElegantNorlin/CheckInService/releases)并解压缩

2. 配置  `\config\application.properties`配置文件
3. 扔到linux服务器上，执行`docker-compose up -d`就部署成功了

**注意：**

如果要修改了配置文件想要生效，建议直接重新建容器：

```
docker-compose down
docker-compose up -d
```

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=ElegantNorlin/CheckInService&type=Date)](https://star-history.com/#ElegantNorlin/CheckInService&Date)

# ‍💻CheckInService

基于SpringBoot开发的京东、掘金定时签到服务（薅羊毛）

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
*   阿里网盘
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
![](https://github.com/ElegantNorlin/CheckInService/blob/main/img/IMG_2844.PNG?raw=true)
掘金签到、抽奖
![](https://github.com/ElegantNorlin/CheckInService/blob/main/img/IMG_2845.PNG?raw=true)
掘金等级成长
![](https://github.com/ElegantNorlin/CheckInService/blob/main/img/IMG_2846.PNG?raw=true)

#### 2.2 [PushDeer推送](https://www.pushdeer.com/)
![](https://github.com/ElegantNorlin/CheckInService/blob/main/img/IMG_2849.PNG?raw=true)
![](https://github.com/ElegantNorlin/CheckInService/blob/main/img/IMG_2848.PNG?raw=true)
![](https://github.com/ElegantNorlin/CheckInService/blob/main/img/IMG_2847.PNG?raw=true)
### 3 😋食用方法(docker compose一键部署)

#### 3.1 克隆项目

    git clone git@github.com:ElegantNorlin/CheckInService.git

下载项目压缩包也可以

#### 3.2 使用[IDEA](https://www.jetbrains.com.cn/idea/)打开该项目修改相关账号配置，并打jar包。

*   修改CheckInService/src/main/resources/application.properties文件中的京东和掘金的cookie、各定时任务触发时间、以及是否执行任务。
*   直接通过maven打成jar包

#### 3.3 将jar包、docker-compose.yml、dockerfile脚本放到服务器的同一文件夹下，执行下列命令(前提需要安装docker compose)

```shell
docker-compose up -d
```

### 4 ⚙更新jar包

前提：Linux服务器安装Docker，并确保能够正常拉取镜像。

#### 4.1 将新的jar包覆盖原来的jar包

#### 4.2 执行下列命令

    docker restart check_in_container

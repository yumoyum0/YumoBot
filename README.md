# YumoBot
[![OSCS Status](https://www.oscs1024.com/platform/badge/YumoBot.svg?size=small)](https://www.murphysec.com/)

[![OSCS Status](https://www.oscs1024.com/platform/badge/YumoBot.svg?size=large)](https://www.murphysec.com/)


一个基于 **mirai** 的qqbot

![](https://yumoimgbed.oss-cn-shenzhen.aliyuncs.com/img/93246317_p13_master1200.jpg)

# 人设

- 本名：芋泥波波

- 别名：芋啵

- 发色：蓝发

- 瞳色：金瞳

- 身高：130cm

- 星座：双鱼座

- 特点：天然呆，吃货，无性，娇憨，中二，腹黑，爱喝芋泥波波奶茶

- 简介：取自其主羽墨之名和自身的bot身份，因其主钟爱芋泥波波奶茶而得名

# 核心架构

![image-20221103180631019](https://yumoimgbed.oss-cn-shenzhen.aliyuncs.com/img/image-20221103180631019.png)

**功能列表**

- 闲聊
- 城市查询
- 批量发图
- 一言

- 课表查询
- 天气查询
- 主动消息生产
- . . . . . .

# 搭建

要运行本项目请先配置好以下部分

- IDEA
- Openjdk19
- Mysql （MariaDB）
- Redis
- Minio
- RabbitMQ
- 

## JDK19配置

看我博客： [羽墨的个人博客 (yumoyumo.top)](https://www.yumoyumo.top/885.html)

## 软件配置

然后clone本仓库或者下载仓库代码，并用IDEA打开。

在`src/main/resources`目录下创建`application.properties`文件，并写入以下信息

```yml
spring.profiles.active=dev
spring.application.name=yumobot
server.port=8088
liteflow.rule-source=liteflow/*.el.xml
liteflow.thread-executor-class=top.yumoyumo.yumobot.config.CustomThreadBuilder
```
本地运行：
在`src/main/resources`目录下创建`application-dev.yml`文件，并写入以下信息

```yml
spring:
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: admin
    password: 123456
    publisher-confirm-type: correlated
    #消息退回
    publisher-returns: true
  mvc:
    path match:
      #解决springboot版本过高导致的空指针异常
      matching-strategy: ant_path_matcher
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/qqbot?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
  redis:
    host: 127.0.0.1 # Redis服务器地址
    database: 0 # Redis数据库索引（默认为0）
    port: 6379 # Redis服务器连接端口
    password: 123456 # Redis服务器连接密码（默认为空）
    jedis:
      pool:
        max-active: 200 # 连接池最大连接数（使用负值表示没有限制）
        max-wait: -1ms # 连接池最大阻塞等待时间（使用负值表示没有限制）
        max-idle: 10 # 连接池中的最大空闲连接
        min-idle: 0 # 连接池中的最小空闲连接
    timeout: 3000ms # 连接超时时间（毫秒

mybatis:
  mapper-locations:
    - classpath:mapper/*.xml
    - classpath*:com/**/mapper/*.xml

minio:
  endpoint: http://127.0.0.1:9000
  accessKey: username
  secretKey: password
  bucketName: images
bot:
  account: 123456
  pwd: 123456
  version: 123456
  mode: 123456

master:
  id: 123456

timetable:
  id: 123456

weather:
  key: 123456

translation:
  appid: 123456
  salt: 123456
  secret: 123456

chat:
  api_key: 123456
  api_secret: 123456
```
生产环境运行：
在`src/main/resources`目录下创建`application-prod.yml`文件，内容同`application-dev.yml`文件，只是把相应的value改成生产环境的即可

完成之后即可运行查看效果。

## 项目部署

因为dockerHub上没有jdk19的镜像，所以需要自己手动构建

详情看我博客： [羽墨的个人博客 (yumoyumo.top)](https://www.yumoyumo.top/885.html)

# 鸣谢

- [Mirai - 提供 QQ Android 协议支持的高效率机器人库](https://github.com/mamoe/mirai)

# 许可证

```
Copyright (C) 2022  Yumoyum0

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
```

###  

FROM ubuntu:20.04
#指明该镜像的作者和其电子邮件
MAINTAINER yumo "Yumoyum0x@gmail.com"

COPY jdk-19_linux-x64_bin.tar.gz /usr/local/
RUN tar -zxvf /usr/local/jdk-19_linux-x64_bin.tar.gz -C /usr/local/


#配置环境变量
ENV JAVA_HOME /usr/local/jdk-19.0.1/
ENV PATH $JAVA_HOME/bin:$PATH
#容器启动时需要执行的命令
CMD ["java","-version"]

FROM yumoyumoyumo/jdk:19

MAINTAINER yumo "yumo1304960237@gmail.com"
# 设置时区
ENV TZ="Asia/Shanghai"
# VOLUME 指定临时文件目录为 /tmp，在主机 /var/lib/docker 目录下创建了一个临时文件并链接到容器的 /tmp
VOLUME /tmp
# 将 jar 放入容器内
ADD QQBot.jar .
ADD config/application-prod.yml /config/
#以下COPY为qqbot项目所需的认证信息和缓存信息，其他项目可删除
COPY device.json /
COPY cache/account.secrets /cache/
COPY cache/servers.json /cache/
COPY cache/session.bin /cache/
# 启动服务
ENTRYPOINT ["java", "--enable-preview","-Xmx512m","-Xms512m","-jar","/QQBot.jar","--spring.profiles.active=prod","-c"]
# 暴露端口，看你项目暴露的端口是什么
EXPOSE 8088
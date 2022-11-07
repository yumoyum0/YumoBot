package top.yumoyumo.yumobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.VirtualThread;

import java.util.concurrent.Future;

/**
 * The type Log controller.
 *
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /9/9 22:30
 */
@RestController
@RequestMapping("/版本")
@Slf4j
public class VersionController {


    /**
     * Log future.
     *
     * @return the future
     */
    @RequestMapping()
    @VirtualThread("版本help")
    public Future<String> log() {
        return new AsyncResult<>(
                """
                        @1.0 ： 2022.9.8
                        - YumoBot正式立项
                        - 项目整体架构搭建
                        - 新增城市信息查询
                        - 新增天气查询
                        - 新增一言
                        - 新增闲聊机器人

                        @1.1 ： 2022.9.9
                        - 优化项目结构
                        - 修复部分情况下多次应答
                        - 优化bot应答机制
                        - 新增版本更新日志查询
                        - 新增bot人设介绍

                        @1.2 ： 2022.9.10
                        - 优化闲聊模式
                        - 新增闲聊模式开关
                        - 新增关键词触发词库

                        @1.3 ： 2022.9.21
                        - 新增图片接口
                        - 新增自动构建图库
                        """
        );
    }
}

package top.yumoyumo.yumobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.OperateLog;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.service.OneMsgService;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /9/8 17:53
 */
@RestController
@RequestMapping("/一言")
@Slf4j
public class OneMsgController {
    @Resource
    private OneMsgService oneMsgService;

    @OperateLog(operDesc = "一言help")
    @RequestMapping("/help")
    @VirtualThread
    public Future<String> help() {
        return new AsyncResult<>(
                """
                        一言help:
                        --------------------
                        /一言 [动画、漫画、游戏、文字、原创、网络、影视、诗词、网易云、哲学、随机]
                        例:
                        /一言 随机
                        """
        );
    }

    @OperateLog(operDesc = "一言")
    @RequestMapping(value = {"/{c}", "", "/"})
    @VirtualThread
    public Future<String> oneMsg(@PathVariable(required = false) String c) {
        return new AsyncResult<>(oneMsgService.oneMsg(c));
    }
}

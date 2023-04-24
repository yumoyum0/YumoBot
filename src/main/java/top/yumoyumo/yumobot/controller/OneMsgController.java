package top.yumoyumo.yumobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.common.Result;
import top.yumoyumo.yumobot.service.OneMsgService;

import javax.annotation.Resource;

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

    @RequestMapping("/help")
    @VirtualThread("一言help")
    public Result help() {
        return Result.success(
                """
                        一言help:
                        --------------------
                        /一言 [动画、漫画、游戏、文字、原创、网络、影视、诗词、网易云、哲学、随机]
                        例:
                        /一言 随机
                        """);
    }

    @RequestMapping(value = {"/{c}", "", "/"})
    @VirtualThread("一言")
    public Result oneMsg(@PathVariable(required = false) String c) {
        return oneMsgService.oneMsg(c);
    }
}

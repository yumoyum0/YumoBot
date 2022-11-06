package top.yumoyumo.yumobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.OperateLog;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.service.ImageService;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * The type Image controller.
 *
 * @Author: yumo
 * @Description: 图片接口
 * @DateTime: 2022 /9/19 18:09
 */
@RestController
@RequestMapping("/图片")
@Slf4j
public class ImageController {

    @Resource
    private ImageService imageService;

    @RequestMapping("/help")
    @OperateLog(operDesc = "图片help")
    @VirtualThread
    public Future<String> help() {
        return new AsyncResult<>(
                """
                        图片help:
                        --------------------
                        /图片 [num] [tag]
                        例:
                        /图片 10
                        /图片 10 原神
                        """
        );
    }

    @RequestMapping(value = {"", "/"})
    @OperateLog(operDesc = "获取图片")
    @VirtualThread
    public Future<String> getImage(@RequestParam(required = false) String tag,
                                   @RequestParam(required = false) String num,
                                   @RequestParam(required = false) String r18) {
        return new AsyncResult<>(imageService.getImage(tag, num, r18));
    }
}

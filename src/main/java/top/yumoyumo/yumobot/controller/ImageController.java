package top.yumoyumo.yumobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.common.Result;
import top.yumoyumo.yumobot.service.ImageService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
    @VirtualThread("图片help")
    public Future<Result> help() {
        return new AsyncResult<>(Result.success(
                """
                        图片help:
                        --------------------
                        /图片 [num] [tag]
                        例:
                        /图片 10
                        /图片 10 原神
                        """)
        );
    }

    @RequestMapping(value = {"", "/"})
    @VirtualThread("获取图片")
    public Future<Result> getImage(@RequestParam(required = false) String tag,
                                   @RequestParam(required = false) String num,
                                   @RequestParam(required = false) String r18) {

        return new AsyncResult<>(Result.success(imageService.getImage(tag, num, r18)));
    }

    @GetMapping("/download")
    public void downloadImages(HttpServletResponse response,
                               @RequestParam(required = false) String tag,
                               @RequestParam(required = false) String num,
                               @RequestParam(required = false) String r18) {
        imageService.download(response, tag, num, r18);
    }
}

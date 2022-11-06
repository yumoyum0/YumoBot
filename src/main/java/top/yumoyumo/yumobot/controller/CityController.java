package top.yumoyumo.yumobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.OperateLog;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.service.CityService;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * The type City controller.
 *
 * @Author: yumo
 * @Description: 城市信息查询
 * @DateTime: 2022 /9/8 13:22
 */
@RestController
@RequestMapping("/城市")
@Slf4j
public class CityController {

    @Resource
    private CityService cityService;


    @RequestMapping(value = {"", "/", "/help"})
    @OperateLog(operDesc = "城市help")
    @VirtualThread
    public Future<String> help() {
        return new AsyncResult<>(
                """
                        城市信息查询help:
                        --------------------
                        [/城市 {城市名,经纬度,ID}]
                        例:
                        /城市 南京
                        /城市 118.76741,32.04154
                        /城市 101190101
                        """);
    }


    @RequestMapping("/{location}")
    @OperateLog(operDesc = "城市查询")
    @VirtualThread
    public Future<String> location(@PathVariable String location) {
        return new AsyncResult<>(cityService.location(location));
    }
}

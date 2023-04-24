package top.yumoyumo.yumobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.common.Result;
import top.yumoyumo.yumobot.service.WeatherService;

import javax.annotation.Resource;

/**
 * @Author: yumo
 * @Description: 天气信息查询
 * @DateTime: 2022 /9/8 12:51
 */
@RestController
@RequestMapping("/天气")
@Slf4j
public class WeatherController {
    @Resource
    private WeatherService weatherService;

    @RequestMapping(value = {"", "/", "/help"})
    @VirtualThread("天气查询help")
    public Result help() {
        return Result.success(
                """
                        实时天气查询help:
                        --------------------
                        [/天气/{城市名,经纬度,ID}]
                        例:\s
                        /天气/南京
                        /天气/118.76741,32.04154
                        /天气/101190101"""
        );
    }

    @RequestMapping("/{location}")
    @VirtualThread("实时天气查询")
    public Result nowWeather(@PathVariable String location) {
        return Result.success(weatherService.nowWeather(location));
    }
}

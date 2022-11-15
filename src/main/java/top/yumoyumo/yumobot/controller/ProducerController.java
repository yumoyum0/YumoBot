package top.yumoyumo.yumobot.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.service.ProducerService;

import javax.annotation.Resource;

/**
 * @Author: yumo
 * @Description: 生产者
 * @DateTime: 2022/10/30 20:04
 **/
@Slf4j
@RestController
@RequestMapping("/msg")
public class ProducerController {
    @Resource
    private ProducerService producerService;


    @RequestMapping("sendCustomMsg")
    @VirtualThread("发送自定义消息")
    public void sendCustomMsg(@RequestParam String content, @RequestParam(required = false, defaultValue = "0") String delay) {
        producerService.sendCustomMsg(content, delay);
    }

    @RequestMapping("sendTimeTableMsg")
    @VirtualThread("发送课表消息")
    public void sendTimeTableMsg(@RequestParam String id) {
        producerService.sendTimeTableMsgFromRedis(id);
    }
}


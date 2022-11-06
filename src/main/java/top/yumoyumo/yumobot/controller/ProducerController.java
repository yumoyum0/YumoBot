package top.yumoyumo.yumobot.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.OperateLog;
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


    @GetMapping("sendDelayMsg")
    @OperateLog(operDesc = "发送延迟消息")
    @VirtualThread
    public void sendDelayMsg(@RequestParam String id) {
        producerService.sendDelayMsg(id);
    }
}

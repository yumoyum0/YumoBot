package top.yumoyumo.yumobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.common.Result;
import top.yumoyumo.yumobot.service.ChatService;

import javax.annotation.Resource;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/3 14:32
 **/
@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Resource
    private ChatService chatService;

    @RequestMapping()
    @VirtualThread("闲聊")
    public Result chat(String content, Integer type, Long from, String fromName, Long to, String toName) {
        return Result.success(chatService.chat(content, type, from, fromName, to, toName));
    }
}

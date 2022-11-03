package top.yumoyumo.yumobot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.annotation.OperateLog;
import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.pojo.ChatBean;
import top.yumoyumo.yumobot.service.ChatService;

import javax.annotation.Resource;
import java.util.concurrent.Future;

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
    @OperateLog(operDesc = "闲聊")
    @VirtualThread
    public Future<String> chat(ChatBean chatBean) {
        return new AsyncResult<>(chatService.chat(chatBean));
    }
}

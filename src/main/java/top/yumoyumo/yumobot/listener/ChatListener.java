package top.yumoyumo.yumobot.listener;


import cn.hutool.http.HttpUtil;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;
import top.yumoyumo.yumobot.util.SpringUtil;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;

/**
 * The type Chat listener.
 *
 * @Author: yumo
 * @Description: 闲聊监听
 * @DateTime: 2022 /9/9 10:56
 */
@Slf4j
public class ChatListener extends SimpleListenerHost {

    public static final String DISPATCH_URL = "http://127.0.0.1:8088";

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        log.error(exception.getMessage());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onMessage(@NotNull GroupMessageEvent event) {
        ExecutorService executorService = SpringUtil.getBean(ExecutorService.class);
        executorService.submit(() -> {
            if (new Random().nextInt(100000) % 999 < 314) {
                long id = event.getSubject().getId();
                if (CloseListener.isClose.get(id)) return;
                String content = ((PlainText) Objects.requireNonNull(event.getMessage().stream().filter(PlainText.class::isInstance).findFirst().orElse(new PlainText("")))).getContent().trim();
                if (!content.contains("/")) {
                    if (!content.equals("")) {
                        HashMap<String, Object> params = new HashMap<>();
                        params.put("content", content);
                        params.put("type", 2);
                        params.put("from", event.getSender().getId());
                        params.put("fromName", event.getSenderName());
                        params.put("to", event.getSource().getTargetId());
                        params.put("toName", event.getSource().getTarget().getName());
                        String s = HttpUtil.get(DISPATCH_URL + "/chat", params);
                        event.getSubject().sendMessage(s);
                    }
                }
            }
        });
    }

}

package top.yumoyumo.yumobot.listener;


import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import top.yumoyumo.yumobot.common.Result;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * The type Chat listener.
 *
 * @Author: yumo
 * @Description: 闲聊监听
 * @DateTime: 2022 /9/9 10:56
 */
@Slf4j
public class ChatListener extends SimpleListenerHost {
    private static final Random random = new Random();

    public static final String DISPATCH_URL = "http://127.0.0.1:8088";

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        log.error(exception.getMessage());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onMessage(@NotNull GroupMessageEvent event) {
        if (CloseListener.sessionStates.get(event.getSubject().getId()).equals(CloseListener.CLOSED) || random.nextInt(100000) % 999 >= 618)
            return;
        String content = event.getMessage().contentToString();
        if (!content.contains("/")) {
            if (!content.equals("")) {
                Map<String, Object> params = Map.of(
                        "content", content,
                        "type", 2,
                        "from", event.getSender().getId(),
                        "fromName", event.getSenderName(),
                        "to", event.getSource().getTargetId(),
                        "toName", event.getSource().getTarget().getName()
                );
                Result result = new Gson().fromJson(HttpUtil.get(DISPATCH_URL + "/chat", params), Result.class);
                event.getSubject().sendMessage(Optional.ofNullable(result.getData()).orElse("喵"));
            }
        }

    }

}

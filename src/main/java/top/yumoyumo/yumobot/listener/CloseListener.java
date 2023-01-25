package top.yumoyumo.yumobot.listener;


import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Close listener.
 *
 * @Author: yumo
 * @Description: 关闭监听
 * @DateTime: 2022 /9/9 20:07
 */
@Slf4j
public class CloseListener extends SimpleListenerHost {

    // 创建一个Map来存储会话状态
    // 会话状态常量
    public static final String OPEN = "open";
    public static final String CLOSED = "closed";
    public static final String IMAGE_ENABLED = "image_enabled";
    public static Map<Long, String> sessionStates = new HashMap<>();

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        log.error(exception.getMessage());
        exception.printStackTrace();
    }

    @NotNull
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMessage(@NotNull MessageEvent event) {
        long id = event.getSubject().getId();
        if (!sessionStates.containsKey(id))
            sessionStates.put(id, OPEN);
        String content = event.getMessage().contentToString();
        handleSessionState(id, content, event);
    }

    /**
     * 检查字符串是否包含指定的任意一个关键字
     *
     * @param text     要检查的字符串
     * @param keywords 关键字列表
     * @return 如果字符串包含指定的任意一个关键字，则返回true；否则返回false
     */
    private boolean containsAny(String text, String... keywords) {
        return Arrays.stream(keywords).anyMatch(text::contains);
    }

    /**
     * 处理会话状态
     *
     * @param id      会话ID
     * @param content 会话内容
     * @param event   会话事件
     */
    private void handleSessionState(long id, String content, @NotNull MessageEvent event) {
        if (containsAny(content, "雪豹闭嘴", "close")) {
            sessionStates.put(id, CLOSED);
            event.getSubject().sendMessage("link break...");
            event.getSubject().sendMessage("呜呜呜呜喵");
        } else if (containsAny(content, "雪豹张嘴", "醒来", "start")) {
            sessionStates.put(id, OPEN);
            event.getSubject().sendMessage("link start!");
            event.getSubject().sendMessage("芋泥啵啵堂堂复活!");
        } else if (containsAny(content, "开启图片", "开启涩图", "开启色图")) {
            sessionStates.put(id, IMAGE_ENABLED);
            event.getSubject().sendMessage("图片功能已开启");
        } else if (containsAny(content, "关闭图片", "关闭涩图", "关闭色图")) {
            sessionStates.put(id, OPEN);
            event.getSubject().sendMessage("图片功能已关闭");
        }
    }
}
package top.yumoyumo.yumobot.listener;


import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The type Close listener.
 *
 * @Author: yumo
 * @Description: 关闭监听
 * @DateTime: 2022 /9/9 20:07
 */
@Slf4j
public class CloseListener extends SimpleListenerHost {
    public static Map<Long, Boolean> isClose = new HashMap<>();

    public static Map<Long, Boolean> isImage = new HashMap<>();

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        log.error(exception.getMessage());
    }

    @NotNull
    @EventHandler(priority = EventPriority.HIGHEST)
    public ListeningStatus onMessage(@NotNull MessageEvent event) {

        long id = event.getSubject().getId();
        if (!isClose.containsKey(id)) isClose.put(id, Boolean.FALSE);
        if (!isImage.containsKey(id)) isImage.put(id, Boolean.FALSE);

        String content = ((PlainText) Objects.requireNonNull(event.getMessage().stream().filter(PlainText.class::isInstance)
                .findFirst().orElse(new PlainText("")))).getContent().trim();
        if (!isClose.get(id) && (content.contains("雪豹闭嘴") || content.contains("close"))) {
            event.getSubject().sendMessage("link break...");
            event.getSubject().sendMessage("呜呜呜呜喵");
            isClose.put(id, Boolean.TRUE);
        } else if (isClose.get(id) && content.contains("雪豹张嘴") || content.contains("醒来") || content.contains("start")) {
            isClose.put(id, Boolean.FALSE);
            event.getSubject().sendMessage("link start!");
            event.getSubject().sendMessage("芋泥啵啵堂堂复活!");
        } else if ((!isImage.get(id)) && (content.contains("开启图片") || content.contains("开启涩图") || content.contains("开启色图"))) {
            isImage.put(id, Boolean.TRUE);
            event.getSubject().sendMessage("图片功能已开启");
        } else if (isImage.get(id) && content.contains("关闭图片") || content.contains("关闭涩图") || content.contains("关闭色图")) {
            isImage.put(id, Boolean.FALSE);
            event.getSubject().sendMessage("图片功能已关闭");
        }
        return ListeningStatus.LISTENING;
    }

}

package top.yumoyumo.yumobot.listener;

import com.yomahub.liteflow.core.FlowExecutor;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;
import top.yumoyumo.yumobot.common.BotContext;
import top.yumoyumo.yumobot.util.SpringUtil;

/**
 * The type Local listener.
 *
 * @Author: yumo
 * @Description: 接口调用监听
 * @DateTime: 2022 /9/9 20:14
 */
@Slf4j
public class DispatcherListener extends SimpleListenerHost {

    public static final String DISPATCH_URL = "http://127.0.0.1:8088";

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        log.error(exception.getMessage(), exception.getCause());
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onMessage(@NotNull MessageEvent event) {
        FlowExecutor flowExecutor = SpringUtil.getBean(FlowExecutor.class);
        flowExecutor.execute2Future("dispatchChain", event, BotContext.class);
    }
}

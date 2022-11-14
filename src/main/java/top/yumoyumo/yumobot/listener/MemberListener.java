package top.yumoyumo.yumobot.listener;

import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.event.events.NudgeEvent;
import net.mamoe.mirai.message.data.*;
import org.jetbrains.annotations.NotNull;
import top.yumoyumo.yumobot.util.SpringUtil;

import java.util.concurrent.ExecutorService;

/**
 * @Author: yumo
 * @Description: 群成员监听
 * @DateTime: 2022/11/3 12:47
 **/
@Slf4j
public class MemberListener extends SimpleListenerHost {

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        log.error(exception.getMessage());
    }

    /**
     * 群成员加入监听
     */
    @NotNull
    @EventHandler(priority = EventPriority.NORMAL)
    public ListeningStatus onMemberJoinMessage(@NotNull MemberJoinEvent event) {
        MessageChain chain = new MessageChainBuilder()
                .append(new At(event.getMember().getId()))
                .append(" 欢迎大傻喵加入我们的大家庭喵！")
                .build();
        event.getGroup().sendMessage(chain);
        return ListeningStatus.LISTENING;
    }

    /**
     * 群成员退出监听
     */
    @NotNull
    @EventHandler(priority = EventPriority.NORMAL)
    public ListeningStatus onMemberLeaveMessage(@NotNull MemberLeaveEvent event) {
        MessageChain chain = new MessageChainBuilder()
                .append(event.getMember().getNick())
                .append(" 永远地离开了我们......")
                .build();
        event.getGroup().sendMessage(chain);
        return ListeningStatus.LISTENING;
    }

    /**
     * 戳一戳监听
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onNudgeMessage(@NotNull NudgeEvent event) {
        if (!event.getFrom().getNick().equals("YumoBot"))
            event.getTarget().nudge().sendTo(event.getSubject());
    }

    /**
     * 闪照监听
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onFlashImageMessage(@NotNull MessageEvent event) {
        FlashImage flashImage = ((FlashImage) (event.getMessage().stream().filter(FlashImage.class::isInstance).findFirst().orElse(null)));
        if (flashImage != null) {
            ForwardMessageBuilder builder = new ForwardMessageBuilder(event.getSubject());
            User sender = event.getSender();
            builder.add(sender, flashImage.getImage());
            event.getSubject().sendMessage(builder.build()).recallIn(1000 * 60);
        }
    }
}

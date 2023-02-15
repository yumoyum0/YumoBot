package top.yumoyumo.yumobot.component.flow;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeIfComponent;
import net.mamoe.mirai.event.events.MessageEvent;
import top.yumoyumo.yumobot.common.BotContext;
import top.yumoyumo.yumobot.constants.RedisKeyConst;
import top.yumoyumo.yumobot.listener.CloseListener;
import top.yumoyumo.yumobot.service.RedisService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/12/5 15:42
 **/
@LiteflowComponent("openImageCmp")
public class OpenImageCmp extends NodeIfComponent {

    @Resource
    private RedisService redisService;

    @Override
    public boolean processIf() throws Exception {
        BotContext context = this.getContextBean(BotContext.class);
        long id = context.getEvent().getSubject().getId();
        if (!CloseListener.sessionStates.get(id).equals(CloseListener.IMAGE_ENABLED)) {
            context.getEvent().getSubject().sendMessage("图片功能未开启");
            return false;
        } else if (redisService.get(RedisKeyConst.getLimitKey(String.valueOf(id))) != null) {
            MessageEvent event = context.getEvent();
            event.getSubject().sendMessage("用户" + id + ": 请在你上次成功请求图片的20s后再次请求");
            return false;
        }
        redisService.set(RedisKeyConst.getLimitKey(String.valueOf(id)), "", 20, TimeUnit.SECONDS);
        return true;
    }
}

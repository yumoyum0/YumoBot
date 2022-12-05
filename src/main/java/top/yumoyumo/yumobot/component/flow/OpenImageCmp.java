package top.yumoyumo.yumobot.component.flow;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeIfComponent;
import top.yumoyumo.yumobot.common.BotContext;
import top.yumoyumo.yumobot.listener.CloseListener;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/12/5 15:42
 **/
@LiteflowComponent("openImageCmp")
public class OpenImageCmp extends NodeIfComponent {

    @Override
    public boolean processIf() throws Exception {
        BotContext context = this.getContextBean(BotContext.class);
        if (!CloseListener.isImage.get(context.getEvent().getSubject().getId())) {
            context.getEvent().getSubject().sendMessage("图片功能未开启");
            return false;
        }
        return true;
    }
}

package top.yumoyumo.yumobot.component.flow;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeSwitchComponent;
import top.yumoyumo.yumobot.common.BotContext;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/12/5 15:02
 **/
@LiteflowComponent("pathCmp")
public class PathCmp extends NodeSwitchComponent {

    @Override
    public String processSwitch() throws Exception {
        BotContext context = this.getContextBean(BotContext.class);
        return switch (context.getPath()) {
            case "/图片" -> "imageChain";
            case default -> "pathVarCmp";
        };
    }

}

package top.yumoyumo.yumobot.component.flow;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import net.mamoe.mirai.event.events.MessageEvent;
import top.yumoyumo.yumobot.common.BotContext;

import java.util.ArrayList;
import java.util.Arrays;

import static top.yumoyumo.yumobot.listener.DispatcherListener.DISPATCH_URL;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/12/5 14:44
 **/
@LiteflowComponent("initCmp")
public class InitCmp extends NodeComponent {


    @Override
    public void process() throws Exception {
        MessageEvent requestData = this.getRequestData();
        String content = requestData.getMessage().contentToString();
        String path = content.substring(0, !content.contains(" ") ? content.length() : content.indexOf(" "));
        ArrayList<Object> paramsList = new ArrayList<>(Arrays.stream(content.substring(path.length()).split(" ")).filter(e -> !e.equals("")).toList());
        BotContext context = this.getContextBean(BotContext.class);
        context.setEvent(requestData);
        context.setContent(content);
        context.setPath(path);
        context.setUrl(DISPATCH_URL + path);
        context.setParamsList(paramsList);
    }

}

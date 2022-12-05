package top.yumoyumo.yumobot.component.flow;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeIfComponent;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/12/5 16:19
 **/
@LiteflowComponent("matchCmp")
public class MatchCmp extends NodeIfComponent {

    @Override
    public boolean processIf() throws Exception {
        MessageEvent requestData = this.getRequestData();
        Matcher matcher = Pattern.compile("^/(版本|人设|城市|天气|一言|图片|help|课表)").
                matcher(requestData.getMessage().contentToString());
        return matcher.find();
    }
}

package top.yumoyumo.yumobot.component.flow;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.web.client.RestTemplate;
import top.yumoyumo.yumobot.common.BotContext;
import top.yumoyumo.yumobot.common.Result;

import javax.annotation.Resource;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/12/5 15:04
 **/
@LiteflowComponent("pathVarCmp")
public class PathVarCmp extends NodeComponent {

    @Resource
    RestTemplate restTemplate;

    @Override
    public void process() throws Exception {
        BotContext context = this.getContextBean(BotContext.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getUrl());
        for (Object o : context.getParamsList())
            stringBuilder.append("/").append(o);

        Result result = restTemplate.getForEntity(stringBuilder.toString(), Result.class).getBody();
        if (result != null && result.getData() != null) {
            context.getEvent().getSubject().sendMessage(result.getData());
        }
    }
}

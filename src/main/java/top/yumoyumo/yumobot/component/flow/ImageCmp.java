package top.yumoyumo.yumobot.component.flow;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import top.yumoyumo.yumobot.common.BotContext;
import top.yumoyumo.yumobot.common.Result;
import top.yumoyumo.yumobot.exception.LocalRuntimeException;
import top.yumoyumo.yumobot.pojo.ImageBean;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/12/5 15:47
 **/
@LiteflowComponent("imageCmp")
@Slf4j
public class ImageCmp extends NodeComponent {

    @Resource
    private ExecutorService executorService;

    @Override
    public void process() throws Exception {
        BotContext context = this.getContextBean(BotContext.class);
        MessageEvent event = context.getEvent();
        ArrayList<Object> paramsList = context.getParamsList();
        HashMap<String, Object> params = new HashMap<>();
        for (int i = 0; i < paramsList.size(); i++)
            params.put(switch (i) {
                case 0 -> "num";
                case 1 -> "tag";
                case 2 -> "r18";
                default -> throw new LocalRuntimeException("参数过多");
            }, paramsList.get(i));
        Result result = new Gson().fromJson(HttpUtil.get(context.getUrl(), params), Result.class);
        if (result != null && result.getData() != null) {
            for (ImageBean.DataDTO dataDTO : (new Gson().fromJson(result.getData(), ImageBean.class)).getData()) {
                executorService.submit(() -> {
                    try (
                            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                            CloseableHttpResponse response = httpClient.execute(new HttpGet(dataDTO.getUrls().getOriginal()));
                            InputStream in = response.getEntity().getContent();
                            ExternalResource resource = ExternalResource.create(in)
                    ) {
                        Image image = ExternalResource.uploadAsImage(resource, event.getSubject());
                        ForwardMessageBuilder builder = new ForwardMessageBuilder(event.getSubject());
                        User sender = event.getSender();
                        builder.add(sender, Objects.requireNonNull(image));
                        builder.add(sender, new PlainText("pid: " + dataDTO.getPid()));
                        builder.add(sender, new PlainText("uid: " + dataDTO.getUid()));
                        builder.add(sender, new PlainText("author: " + dataDTO.getAuthor()));
                        event.getSubject().sendMessage(builder.build()).recallIn(1000 * 30);
                    } catch (Exception exception) {
                        log.error(exception.getMessage(), exception.getCause());
                    }
                });
            }
        }
    }
}

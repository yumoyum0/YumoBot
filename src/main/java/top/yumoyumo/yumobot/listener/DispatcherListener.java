package top.yumoyumo.yumobot.listener;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.codec.binary.Base64;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import top.yumoyumo.yumobot.common.Result;
import top.yumoyumo.yumobot.exception.LocalRuntimeException;
import top.yumoyumo.yumobot.pojo.ImageBean;
import top.yumoyumo.yumobot.util.SpringUtil;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Local listener.
 *
 * @Author: yumo
 * @Description: 接口调用监听
 * @DateTime: 2022 /9/9 20:14
 */
@Slf4j
public class DispatcherListener extends SimpleListenerHost {
    @Resource
    RestTemplate restTemplate;

    public static final String DISPATCH_URL = "http://127.0.0.1:8088";

    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        log.error(exception.getMessage(), exception.getCause());
    }


    @EventHandler(priority = EventPriority.LOW)
    public void onMessage(@NotNull MessageEvent event) throws IOException { // 可以抛出任何异常, 将在 handleException 处理
        String content = ((PlainText) Objects.requireNonNull(event.getMessage().stream().filter(PlainText.class::isInstance).findFirst().orElse(null))).getContent().trim();
        Matcher matcher = Pattern.compile("^/(版本|人设|城市|天气|一言|图片|help|课表|)[\\s]*([^\\s]*)[\\s]*([^\\s]*)[\\s]*([^\\s]*)$").matcher(content);
        if (matcher.find()) {
            final HashMap<String, Object> params = new HashMap<>();
            String p1 = matcher.group(1).equals("") ? "" : "/" + matcher.group(1);
            String p2 = matcher.group(2).equals("") || p1.equals("") ? "" : matcher.group(2);
            String p3 = matcher.group(3).equals("") || p2.equals("") ? "" : matcher.group(3);
            String p4 = matcher.group(4).equals("") || p3.equals("") ? "" : matcher.group(4);
            String url = DISPATCH_URL + p1;
            ExecutorService executorService = SpringUtil.getBean(ExecutorService.class);
            switch (p1) {
                case "/图片": {
                    if (!CloseListener.isImage.get(event.getSubject().getId())) {
                        event.getSubject().sendMessage("图片功能未开启");
                        return;
                    }
                    params.put("num", p2);
                    params.put("tag", p3);
                    params.put("r18", p4);
                    Result result = restTemplate.getForEntity(url, Result.class, params).getBody();
                    if (result.getData() != null) {

                        for (ImageBean.DataDTO dataDTO : ((ImageBean) result.getData()).getData()) {
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
                    break;
                }
//                case "/语音": {
//                    params.put("content", p3);
//
//                    FileUtil.writeBytes(Base64.decodeBase64(s), new File("src/main/resources/" + p3 + ".wav"));
//                    InputStream in = new ByteArrayInputStream(Base64Decoder.decode(s));
//                    ExternalResource resource = ExternalResource.Companion.create(in);
//                    Voice audio = ExternalResource.uploadAsVoice(resource, event.getSubject());
//                    resource.close();
//                    event.getSubject().sendMessage(audio);
//                    break;
//                }
                case "/版本":
                case "/城市":
                case "/一言":
                case "/help":
                case "/人设":
                case "/天气":
                case "/课表": {
                    url += (p2.equals("") ? "" : "/" + p2) + (p3.equals("") ? "" : "/" + p3);
                    Result result = restTemplate.getForEntity(url, Result.class).getBody();
                    if (result.getData() != null) {
                        MessageChain chain = new MessageChainBuilder()
                                .append((String) result.getData())
                                .build();
                        event.getSubject().sendMessage(chain);
                    }
                    break;
                }
            }
        }
    }
}

package top.yumoyumo.yumobot.service.impl;

import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.yumoyumo.yumobot.pojo.ChatBean;
import top.yumoyumo.yumobot.pojo.MoliBean;
import top.yumoyumo.yumobot.service.ChatService;

import javax.annotation.Resource;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/3 14:36
 **/
@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    private RestTemplate restTemplate;

    @Value("${chat.api_key}")
    private String API_KEY;
    @Value("${chat.api_secret}")
    private String API_SECRET;
    public final String Chat_URL = "https://api.mlyai.com/reply";

    @Override
    public String chat(ChatBean chatBean) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Api-Key", API_KEY);
        headers.add("Api-Secret", API_SECRET);

        JSONObject body = new JSONObject();
        body.set("content", chatBean.getContent());
        body.set("type", chatBean.getType());
        body.set("from", chatBean.getFrom());
        body.set("fromName", chatBean.getFromName());
        body.set("to", chatBean.getTo());
        body.set("toName", chatBean.getToName());
        String s = "";
        HttpEntity<String> formEntity = new HttpEntity<String>(body.toString(), headers);
        MoliBean m = restTemplate.postForEntity(Chat_URL, formEntity, MoliBean.class).getBody();
        s = m.getData().get(0).getContent();
        s = s.replaceAll("[,.?!;，。！？；]", "喵");
        s += "喵";
        return s;
    }
}

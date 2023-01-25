package top.yumoyumo.yumobot.service.impl;


import com.google.gson.Gson;
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
import java.util.Optional;

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
    public String chat(String content, Integer type, Long from, String fromName, Long to, String toName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Api-Key", API_KEY);
        headers.add("Api-Secret", API_SECRET);
        HttpEntity<String> formEntity = new HttpEntity<String>(new Gson().toJson(new ChatBean(content, type, from, fromName, to, toName)), headers);
        MoliBean m = restTemplate.postForEntity(Chat_URL, formEntity, MoliBean.class).getBody();
        Optional<String> s = m.getData().stream().map(MoliBean.DataDTO::getContent).findFirst();
        return s.map(str -> str.replaceAll("[,.?!;，。！？；]", "喵") + "喵").orElse("");
    }
}

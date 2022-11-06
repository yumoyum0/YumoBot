package top.yumoyumo.yumobot.controller;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import top.yumoyumo.yumobot.pojo.TranslationBean;
import top.yumoyumo.yumobot.pojo.nyaru.NyaruPushResponseBean;
import top.yumoyumo.yumobot.pojo.nyaru.NyaruStatusResponseBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

/**
 * The type Audio controller.
 *
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /10/2 22:29
 */
@RestController
@RequestMapping("/语音")
@Slf4j
@Deprecated
public class AudioController {
    @Resource
    private RestTemplate restTemplate;

    @Value("${translation.appid}")
    public String APPID;
    @Value("${translation.salt}")
    public String SALT;
    @Value("${translation.secret}")
    public String SECRET;


    @RequestMapping("/猫雷")
    public String nyaru(@RequestParam String content) throws JsonProcessingException {

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("q", content);
        paramMap.put("from", "auto");
        paramMap.put("to", "jp");
        paramMap.put("appid", APPID);
        paramMap.put("salt", SALT);
        paramMap.put("sign", MD5.create().digestHex(APPID + content + SALT + SECRET));
        content = new ObjectMapper().readValue(HttpUtil.get("https://fanyi-api.baidu.com/api/trans/vip/translate", paramMap), TranslationBean.class).getTransResult().get(0).getDst();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject pushBody = new JSONObject();
        pushBody.set("fn_index", 0);
        pushBody.set("data", content);
        pushBody.set("action", "predict");
        pushBody.set("session_hash", "o2peodvaem");
        HttpEntity<String> pushFormEntity = new HttpEntity<String>(pushBody.toString(), headers);
        NyaruPushResponseBean nyaruPushResponseBean = restTemplate.postForEntity("https://hf.space/embed/innnky/vits-nyaru/api/queue/push/",
                pushFormEntity, NyaruPushResponseBean.class).getBody();
        JSONObject statusBody = new JSONObject();
        statusBody.set("hash", nyaruPushResponseBean.getHash());
        HttpEntity<String> statusFormEntity = new HttpEntity<String>(statusBody.toString(), headers);

        NyaruStatusResponseBean nyaruStatusResponseBean = null;
        while (true) {
            try {
                nyaruStatusResponseBean = restTemplate.postForEntity("https://hf.space/embed/innnky/vits-nyaru/api/queue/status/",
                        statusFormEntity, NyaruStatusResponseBean.class).getBody();
                if (nyaruStatusResponseBean.getData() != null) break;
            } catch (Exception e) {
                log.warn(e.getMessage());
                log.warn(nyaruPushResponseBean.getHash());
            }
        }
        String res = nyaruStatusResponseBean.getData().getData().get(1);
        return res.substring(res.indexOf(",") + 1);
    }
}

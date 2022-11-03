package top.yumoyumo.yumobot.service.impl;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import top.yumoyumo.yumobot.pojo.HitikotoBean;
import top.yumoyumo.yumobot.service.OneMsgService;

import java.util.HashMap;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/3 15:36
 **/
@Service
public class OneMsgServiceImpl implements OneMsgService {

    public static final String ONEMSG_URL = "https://v1.hitokoto.cn/";

    private static final HashMap<String, String> map = new HashMap<>() {{
        put("动画", "a");
        put("动漫", "a");
        put("漫画", "b");
        put("游戏", "c");
        put("文字", "d");
        put("原创", "e");
        put("网络", "f");
        put("影视", "h");
        put("诗词", "i");
        put("网易云", "j");
        put("哲学", "k");
    }};

    @Override
    public String oneMsg(String c) {
        HashMap<String, Object> paramMap = new HashMap<>();
        HitikotoBean hitikotoBean = null;
        if (c != null && map.containsKey(c)) {
            paramMap.put("c", map.get(c));
            hitikotoBean = new Gson().fromJson(HttpUtil.get(ONEMSG_URL, paramMap), HitikotoBean.class);
        } else {
            hitikotoBean = new Gson().fromJson(HttpUtil.get(ONEMSG_URL), HitikotoBean.class);
        }
        StringBuilder builder = new StringBuilder();
        builder.append(hitikotoBean.getHitokoto());
        builder.append("\n\t—— 《").append(hitikotoBean.getFrom()).append("》 ");
        String fromWho = hitikotoBean.getFromWho();
        builder.append(fromWho == null ? "" : fromWho);
        return builder.toString();
    }
}

package top.yumoyumo.yumobot.service;

import top.yumoyumo.yumobot.pojo.ChatBean;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/3 14:35
 **/
public interface ChatService {
    String chat(String content, Integer type, Long from, String fromName, Long to, String toName);
}

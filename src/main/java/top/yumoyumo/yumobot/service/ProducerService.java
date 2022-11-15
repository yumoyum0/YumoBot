package top.yumoyumo.yumobot.service;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/3 15:46
 **/
public interface ProducerService {

    void sendCustomMsg(String content, String delay);

    void sendTimeTableMsgFromRedis(String id);
}

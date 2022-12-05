package top.yumoyumo.yumobot.common;

import net.mamoe.mirai.event.events.MessageEvent;

import java.util.ArrayList;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/12/5 13:48
 **/
public class BotContext {
    private MessageEvent event;
    private String content;
    private String path;
    private String url;
    private ArrayList<Object> paramsList;

    public MessageEvent getEvent() {
        return event;
    }

    public void setEvent(MessageEvent event) {
        this.event = event;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<Object> getParamsList() {
        return paramsList;
    }

    public void setParamsList(ArrayList<Object> paramsList) {
        this.paramsList = paramsList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

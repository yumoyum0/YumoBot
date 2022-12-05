package top.yumoyumo.yumobot.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/3 15:40
 **/
public interface ImageService {
    String getImage(String tag, String num, String r18);

    void download(HttpServletResponse response, String tag, String num, String r18);
}

package top.yumoyumo.yumobot.service;

import top.yumoyumo.yumobot.pojo.URLNameBean;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/22 15:33
 **/
public interface FileService {

    void download(HttpServletResponse response, String zipName, List<URLNameBean.DetailDTO> details);
}

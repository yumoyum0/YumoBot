package top.yumoyumo.yumobot.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.yumoyumo.yumobot.pojo.URLNameBean;
import top.yumoyumo.yumobot.service.FileService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/22 15:30
 **/
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Resource
    FileService fileService;


    @RequestMapping("/download")
    public void download(HttpServletResponse response,
                         @RequestBody String urlName) {
        URLNameBean urlNameBean = new Gson().fromJson(urlName, URLNameBean.class);
        fileService.download(response, urlNameBean.getFormToken(), urlNameBean.getDetails());
    }
}

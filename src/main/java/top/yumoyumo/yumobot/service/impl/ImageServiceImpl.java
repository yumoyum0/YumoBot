package top.yumoyumo.yumobot.service.impl;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yumoyumo.yumobot.service.ImageService;
import top.yumoyumo.yumobot.service.MinioService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/3 15:42
 **/
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private static final String IMAGE_URL = "https://api.lolicon.app/setu/v2";
    @Resource
    private MinioService minioService;
    @Resource
    private ExecutorService executorService;

    @Override
    public String getImage(String tag, String num, String r18) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("num", num);
        paramMap.put("tag", tag);
        paramMap.put("r18", r18);
        String res = HttpUtil.get(IMAGE_URL, paramMap);
        executorService.submit(() -> {
            minioService.upload(res);
        });
        return res;
    }
}

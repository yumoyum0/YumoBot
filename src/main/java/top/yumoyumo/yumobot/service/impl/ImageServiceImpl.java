package top.yumoyumo.yumobot.service.impl;

import cn.hutool.http.HttpUtil;
import org.springframework.stereotype.Service;
import top.yumoyumo.yumobot.service.ImageService;
import top.yumoyumo.yumobot.service.MinioService;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/3 15:42
 **/
@Service
public class ImageServiceImpl implements ImageService {

    private static final String IMAGE_URL = "https://api.lolicon.app/setu/v2";
    @Resource
    private MinioService minioService;

    @Override
    public String getImage(String tag, String num, String r18) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("num", num);
        paramMap.put("tag", tag);
        paramMap.put("r18", r18);
        String res = HttpUtil.get(IMAGE_URL, paramMap);
        minioService.upload(res);
        return res;
    }
}

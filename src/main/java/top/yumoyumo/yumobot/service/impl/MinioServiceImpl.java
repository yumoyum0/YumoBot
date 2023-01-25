package top.yumoyumo.yumobot.service.impl;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yumoyumo.yumobot.pojo.ImageBean;
import top.yumoyumo.yumobot.service.MinioService;
import top.yumoyumo.yumobot.util.FileUtil;
import top.yumoyumo.yumobot.util.MinioUtil;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /9/21 16:19
 */
@Service
@Slf4j
public class MinioServiceImpl implements MinioService {
    @Resource
    private ExecutorService executorService;
    @Resource
    MinioUtil minioUtil;

    @Override
    public void upload(String source) {
        ImageBean imageBean = new Gson().fromJson(source, ImageBean.class);
        Boolean r18 = imageBean.getData().get(0).getR18();
        imageBean.getData().forEach((dataDTO) -> {
            executorService.submit(() -> {
                String filename = dataDTO.getPid() + "." + dataDTO.getExt();
                minioUtil.upload(FileUtil.urlToMultipartFile(dataDTO.getUrls().getOriginal(), filename), r18 ? "r18" : "images");
            });
        });

    }


}

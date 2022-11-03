package top.yumoyumo.yumobot.service.impl;

import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.pojo.ImageBean;
import top.yumoyumo.yumobot.service.MinioService;
import top.yumoyumo.yumobot.util.FileUtil;
import top.yumoyumo.yumobot.util.MinioUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * The type Minio service.
 *
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /9/21 16:19
 */
@Service
@Slf4j
public class MinioServiceImpl implements MinioService {
    @Resource
    private ExecutorService executorService;
    /**
     * The Minio util.
     */
    @Resource
    MinioUtil minioUtil;

    @Override
    @VirtualThread
    public void upload(String source) {
        System.out.println(Thread.currentThread());
        long start = System.currentTimeMillis();
        ImageBean imageBean = new Gson().fromJson(source, ImageBean.class);
        Boolean r18 = imageBean.getData().get(0).getR18();

        imageBean.getData().forEach((dataDTO) -> {
            executorService.submit(() -> {
                System.out.println(Thread.currentThread());
                String filename = dataDTO.getPid() + "." + dataDTO.getExt();
                try {
                    minioUtil.upload(new MultipartFile[]{FileUtil.urlToMultipartFile(dataDTO.getUrls().getOriginal(), filename)}, r18 ? "r18" : "images");
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        });

    }
}

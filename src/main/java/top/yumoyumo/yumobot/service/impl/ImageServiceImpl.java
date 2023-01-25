package top.yumoyumo.yumobot.service.impl;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.yumoyumo.yumobot.exception.LocalRuntimeException;
import top.yumoyumo.yumobot.pojo.ImageBean;
import top.yumoyumo.yumobot.service.ImageService;
import top.yumoyumo.yumobot.service.MinioService;
import top.yumoyumo.yumobot.util.CompressDownloadUtil;
import top.yumoyumo.yumobot.util.FileUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/3 15:42
 **/
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    public static final String IMAGE_URL = "https://api.lolicon.app/setu/v2";
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
        executorService.submit(() -> minioService.upload(res));
        return res;
    }

    @Override
    public void download(HttpServletResponse response, String tag, String num, String r18) {
        List<File> files = new ArrayList<>();
        try {
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("num", num);
            paramMap.put("tag", tag);
            paramMap.put("r18", r18);
            String res = HttpUtil.get(IMAGE_URL, paramMap);
            ImageBean imageBean = new Gson().fromJson(res, ImageBean.class);

            for (ImageBean.DataDTO dataDTO : imageBean.getData()) {
                String filename = dataDTO.getPid() + "." + dataDTO.getExt();
                files.add(FileUtil.urlToFile(dataDTO.getUrls().getOriginal(), filename));
            }
            String downloadName = tag + num + ".zip";
            CompressDownloadUtil.setDownloadResponse(response, downloadName);
            CompressDownloadUtil.compressZip(files, response.getOutputStream());

        } catch (IOException e) {
            throw new LocalRuntimeException(e);
        } finally {
            files.forEach(File::delete);
        }
    }
}
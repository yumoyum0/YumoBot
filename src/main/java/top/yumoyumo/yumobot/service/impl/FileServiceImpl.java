package top.yumoyumo.yumobot.service.impl;

import org.springframework.stereotype.Service;
import top.yumoyumo.yumobot.exception.LocalRuntimeException;
import top.yumoyumo.yumobot.pojo.URLNameBean;
import top.yumoyumo.yumobot.service.FileService;
import top.yumoyumo.yumobot.util.CompressDownloadUtil;
import top.yumoyumo.yumobot.util.FileUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022/11/22 15:33
 **/
@Service
public class FileServiceImpl implements FileService {

    @Override
    public void download(HttpServletResponse response, String zipName, List<URLNameBean.DetailDTO> details) {
        List<File> files = new ArrayList<>();
        try {
            for (URLNameBean.DetailDTO detail : details) {
                String name = detail.getName();
                for (String url : detail.getUrls()) {
                    if (url.contains("sukang")) files.add(FileUtil.urlToFile(url, name + "_苏康码.png"));
                    else if (url.contains("xingcheng")) files.add(FileUtil.urlToFile(url, name + "_行程码.png"));
                    else if (url.contains("vaccine")) files.add(FileUtil.urlToFile(url, name + "_疫苗.png"));
                }
            }
            CompressDownloadUtil.setDownloadResponse(response, zipName + ".zip");
            CompressDownloadUtil.compressZip(files, response.getOutputStream());
        } catch (IOException e) {
            throw new LocalRuntimeException(e);
        } finally {
            files.forEach(File::delete);
        }
    }
}

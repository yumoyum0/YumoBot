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
import java.util.Objects;
import java.util.stream.Collectors;

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
            CompressDownloadUtil.setDownloadResponse(response, zipName + ".zip");
            files = details.stream()
                    .flatMap(detail -> detail.getUrls().stream()
                            .map(url -> {
                                String name = detail.getName();
                                if (url.contains("sukang")) return FileUtil.urlToFile(url, name + "_苏康码.png");
                                else if (url.contains("xingcheng"))
                                    return FileUtil.urlToFile(url, name + "_行程码.png");
                                else if (url.contains("vaccine")) return FileUtil.urlToFile(url, name + "_疫苗.png");
                                return null;
                            }))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            CompressDownloadUtil.compressZip(files, response.getOutputStream());
        } catch (IOException e) {
            throw new LocalRuntimeException(e);
        } finally {
            files.forEach(File::delete);
        }
    }
}

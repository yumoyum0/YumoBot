package top.yumoyumo.yumobot.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @author yumo
 * @date 2021/11/21 13:30
 */
@Slf4j
public class CompressDownloadUtil {

    private CompressDownloadUtil() {
    }

    /**
     * 设置下载响应头
     */
    public static HttpServletResponse setDownloadResponse(HttpServletResponse response, String downloadName) throws UnsupportedEncodingException {
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(downloadName, "UTF-8"));
        response.addHeader("Access-Control-Allow-Origin", "*"); // 实现跨域
        return response;
    }

    public static Integer[] toIntegerArray(String param) {
        return Arrays.stream(param.split(","))
                .map(Integer::valueOf)
                .toArray(Integer[]::new);
    }

    /**
     * 将多个文件压缩到指定输出流中
     *
     * @param files        需要压缩的文件列表
     * @param outputStream 压缩到指定的输出流
     */
    public static void compressZip(List<File> files, OutputStream outputStream) {
        // 包装成ZIP格式输出流
        try (ZipOutputStream zipOutStream = new ZipOutputStream(new BufferedOutputStream(outputStream))) {
            // 设置压缩方法
            zipOutStream.setMethod(ZipOutputStream.DEFLATED);
            // 将多文件循环写入压缩包
            for (File file : files) {
                FileInputStream filenputStream = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                filenputStream.read(data);
                // 添加ZipEntry，并ZipEntry中写入文件流，这里，加上i是防止要下载的文件有重名的导致下载失败
                zipOutStream.putNextEntry(new ZipEntry(file.getName()));
                zipOutStream.write(data);
                filenputStream.close();
                zipOutStream.closeEntry();
            }
        } catch (IOException e) {
            log.error(CompressDownloadUtil.class.getName(), "downloadallfiles", e);
        } finally {
            try {
                if (Objects.nonNull(outputStream)) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error(CompressDownloadUtil.class.getName(), "downloadallfiles", e);
            }
        }
    }

    public static void compressFileItemZip(List<MultipartFile> files, OutputStream outputStream) {
        // 包装成ZIP格式输出流
        try (ZipOutputStream zipOutStream = new ZipOutputStream(new BufferedOutputStream(outputStream))) {
            // 设置压缩方法
            zipOutStream.setMethod(ZipOutputStream.DEFLATED);
            // 将多文件循环写入压缩包
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                InputStream filenputStream = file.getInputStream();
                byte[] data = new byte[(int) file.getSize()];
                filenputStream.read(data);
                // 添加ZipEntry，并ZipEntry中写入文件流，这里，加上i是防止要下载的文件有重名的导致下载失败
                zipOutStream.putNextEntry(new ZipEntry(i + "-" + file.getName()));
                zipOutStream.write(data);
                filenputStream.close();
                zipOutStream.closeEntry();
            }
        } catch (IOException e) {
            log.error(CompressDownloadUtil.class.getName(), "downloadallfiles", e);
        } finally {
            try {
                if (Objects.nonNull(outputStream)) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error(CompressDownloadUtil.class.getName(), "downloadallfiles", e);
            }
        }
    }

    private static HttpResponse invokeGetFile(String url, CloseableHttpClient httpclient) {

        HttpResponse response;
        try {
            HttpGet get = new HttpGet(url);
            get.setHeader("Content-type", "UTF-8");
            get.setHeader("Connection", "close");
            response = httpclient.execute(get);

        } catch (Exception e) {
            log.error("请求文件异常");
            return null;
        }
        return response;
    }

    public static void compressZipByName(List<String> files, OutputStream outputStream) {
        // 包装成ZIP格式输出流
        try (ZipOutputStream zipOutStream = new ZipOutputStream(new BufferedOutputStream(outputStream))) {
            //  设置压缩方法
            zipOutStream.setMethod(ZipOutputStream.DEFLATED);
            // 将多文件循环写入压缩包
            for (int i = 0; i < files.size(); i++) {
                CloseableHttpClient httpclient = HttpClients.createMinimal();
                String fileName = getFileName(files.get(i));
                HttpResponse response = invokeGetFile(files.get(i), httpclient);
                InputStream inputStream = response.getEntity().getContent();
                response.getEntity().getContentLength();
                byte[] data = new byte[(int) response.getEntity().getContentLength()];
                inputStream.read(data);
                //  添加ZipEntry，并ZipEntry中写入文件流，这里，加上i是防止要下载的文件有重名的导致下载失败
                zipOutStream.putNextEntry(new ZipEntry(i + "-" + fileName));
                zipOutStream.write(data);
                inputStream.close();
                zipOutStream.closeEntry();
                httpclient.close();
            }
        } catch (IOException e) {
            log.error(CompressDownloadUtil.class.getName(), "downloadallfiles", e);
        } finally {
            try {
                if (Objects.nonNull(outputStream)) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error(CompressDownloadUtil.class.getName(), "downloadallfiles", e);
            }
        }
    }

    /**
     * 获取文件名
     *
     * @param fileUrl 文件地址
     * @return 文件名
     */
    private static String getFileName(String fileUrl) {
        String[] nameArr = fileUrl.split("/");
        return nameArr[nameArr.length - 1];
    }


    /**
     * 下载文件
     *
     * @param outputStream 下载输出流
     * @param zipFilePath  需要下载文件的路径
     */
    public static void downloadFile(OutputStream outputStream, String zipFilePath) {
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            // 需要下载压塑包文件不存在
            return;
        }
        try (FileInputStream inputStream = new FileInputStream(zipFile)) {
            byte[] data = new byte[(int) zipFile.length()];
            inputStream.read(data);
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            log.error(CompressDownloadUtil.class.getName(), "downloadZip", e);
        } finally {
            try {
                if (Objects.nonNull(outputStream)) {
                    outputStream.close();
                }
            } catch (IOException e) {
                log.error(CompressDownloadUtil.class.getName(), "downloadZip", e);
            }
        }
    }

    /**
     * 删除指定路径的文件
     *
     * @param filepath 文件路径
     */
    public static void deleteFile(String filepath) {
        File file = new File(filepath);
        deleteFile(file);
    }

    /**
     * 删除指定文件
     *
     * @param file 文件
     */
    public static void deleteFile(File file) {
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

}

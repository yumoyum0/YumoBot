package top.yumoyumo.yumobot.util;


import top.yumoyumo.yumobot.exception.LocalRuntimeException;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;


/**
 * The type Minio util.
 *
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /9/20 13:10
 */
@Slf4j
@Component
public class MinioUtil {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;


    /**
     * The Minio client.
     */
    @Resource
    MinioClient minioClient;


    /**
     * Exist bucket.
     *
     * @param name the name
     */
    //判断是否存在对应的桶，如果没有就创建一个
    public void existBucket(String name) {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
            if (!exists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
            }
        } catch (Exception e) {
            throw new LocalRuntimeException("创建桶失败");
        }
    }

    /**
     * Upload list.
     *
     * @param multipartFiles the multipart files
     * @param bucketName     the bucket name
     * @return the list
     */
    public void upload(MultipartFile[] multipartFiles, String bucketName) {
        ExecutorService executorService = SpringUtil.getBean(ExecutorService.class);
        for (MultipartFile file : multipartFiles) {
            executorService.submit(() -> {
                uploadSimpleFile(file, bucketName);
            });
        }
    }

    public void upload(MultipartFile file, String bucketName) {
        uploadSimpleFile(file, bucketName);
    }

    private void uploadSimpleFile(MultipartFile file, String bucketName) {
        try {
            String fileName = file.getOriginalFilename();
            if (StringUtils.isEmpty(fileName)) {
                throw new LocalRuntimeException("文件名为空");
            }
            log.info("上传Minio文件名{}", fileName);
            InputStream in = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    //在 partSize 填入-1时表示大小不确定
                    .stream(in, in.available(), -1)
                    .contentType(file.getContentType())
                    .build()
            );
            log.info("上传Minio文件{}完成", fileName);
        } catch (Exception e) {
            throw new LocalRuntimeException(e);
        }
    }

    /**
     * Download response entity.
     *
     * @param fileName   the file name
     * @param bucketName the bucket name
     * @return the response entity
     */
    public ResponseEntity<byte[]> download(String fileName, String bucketName) {
        ResponseEntity<byte[]> responseEntity = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName).object(fileName).build());
            out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
            //封装返回值
            byte[] bytes = out.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            try {
                headers.add("Content-Disposition",
                        "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
            }
            headers.setContentLength(bytes.length);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setAccessControlExposeHeaders(Collections.singletonList("*"));
            responseEntity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseEntity;
    }

    /**
     * Remove object.
     *
     * @param bucketName the bucket name
     * @param objectName the object name
     */
//删除对应桶的对应文件
    public void removeObject(String bucketName, String objectName) {
        try {
            RemoveObjectArgs objectArgs = RemoveObjectArgs.builder().object(objectName)
                    .bucket(bucketName).build();
            minioClient.removeObject(objectArgs);
        } catch (Exception e) {
            throw new LocalRuntimeException("文件删除失败");
        }
    }

    /**
     * Gets object url.
     *
     * @param bucketName the bucket name
     * @param objectName the object name
     * @param expires    the expires
     * @return the object url
     */
//获取文件外链
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        try {
            GetPresignedObjectUrlArgs objectArgs = GetPresignedObjectUrlArgs.builder().object(objectName)
                    .bucket(bucketName)
                    .expiry(expires).build();
            String url = minioClient.getPresignedObjectUrl(objectArgs);
            return URLDecoder.decode(url, "UTF-8");
        } catch (Exception e) {
            log.error("文件路径获取失败" + e.getMessage());
        }
        return null;
    }

    /**
     * Gets minio file.
     *
     * @param bucketName the bucket name
     * @param objectName the object name
     * @return the minio file
     */
//获取文件流
    public InputStream getMinioFile(String bucketName, String objectName) {
        InputStream inputStream = null;
        try {
            GetObjectArgs objectArgs = GetObjectArgs.builder().object(objectName)
                    .bucket(bucketName).build();
            inputStream = minioClient.getObject(objectArgs);
        } catch (Exception e) {
            log.error("文件获取失败" + e.getMessage());
        }
        return inputStream;
    }

    /**
     * 获取某一个存储对象的下载链接
     *
     * @param bucketName 桶名
     * @param method     方法类型
     * @param objectName 对象名
     * @return url 下载链接
     * @throws ServerException           服务异常
     * @throws InsufficientDataException the insufficient data exception
     * @throws ErrorResponseException    the error response exception
     * @throws IOException               the io exception
     * @throws NoSuchAlgorithmException  the no such algorithm exception
     * @throws InvalidKeyException       the invalid key exception
     * @throws InvalidResponseException  the invalid response exception
     * @throws XmlParserException        the xml parser exception
     * @throws InternalException         the internal exception
     */
    public String getObjectUrl(String bucketName, Method method, String objectName)
            throws ServerException, InsufficientDataException,
            ErrorResponseException, IOException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(method)
                .bucket(bucketName)
                .object(objectName).build());
    }
}

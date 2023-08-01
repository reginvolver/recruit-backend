package com.yundingshuyuan.recruit.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件上传工具类
 */
@Slf4j
public class FileUploadUtils implements DisposableBean {
    @Value("${aliyunOSS.endPoint}")
    private String endPoint;
    @Value("${aliyunOSS.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyunOSS.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyunOSS.bucketName}")
    private String bucketName;
    /**
     * 上传核心
     */
    private OSS ossClient;

    public FileUploadUtils() {
        ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
    }

    /**
     * 流式上传文件 以指定名称储存 并返回URL
     * @param file
     * @return 返回图片URL
     */
    public String fileUpload(MultipartFile file) {
        String filename = normalizeFilename(file);
        try {
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucketName, filename, inputStream);
            // 不考虑文件夹
            return "https://" + bucketName + "." + endPoint + "/" + filename;
        } catch (IOException e) {
            log.error("上传出错", e);
        }finally {
            ossClient.shutdown();
        }
        return null;
    }


    /**
     * 统一上传的文件名
     * yyyy.MM.dd-uuid-filename
     * @param file
     * @return 统一后文件名
     */
    private String normalizeFilename(MultipartFile file) {
        String filename = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        String preifxTime = DateTime.now().toString("yyyy/MM/dd");
        //拼接成完整的文件名。
        return preifxTime + "-" + uuid + "-" + filename;
    }

    /**
     * 流式上传文件 以指定名称储存 并返回URL
     *
     * @param fileInputStream
     * @param filename
     * @return
     */
    public String fileUpload(InputStream fileInputStream, String filename) {
        try {
            ossClient.putObject(bucketName, filename, fileInputStream);
            // 不考虑文件夹
            return "https://" + bucketName + "." + endPoint + "/" + filename;
        } finally {
            ossClient.shutdown();
        }
    }


    @Override
    public void destroy() throws Exception {
        ossClient.shutdown();
    }
}

package com.yundingshuyuan.recruit.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件上传工具类
 */
@Slf4j
public class FileUploadUtils {
    @Value("${aliyunOSS.endPoint}")
    private String endPoint;
    @Value("${aliyunOSS.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyunOSS.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyunOSS.bucketName}")
    private String bucketName;

    /**
     * 获得初始化 OSS
     * @return OSS
     */
    private OSS getInitOss() {
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        return ossClient;
    }

    /**
     * 流式上传文件 以指定名称储存 并返回URL
     * @param file
     * @return 返回图片URL
     */
    public String fileUpload(MultipartFile file) {
        OSS ossClient = getInitOss();
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


    private String normalizeFilename(MultipartFile file) {
        String filename = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        String preifxTime = DateTime.now().toString("yyyy/MM/dd");
        //拼接成完整的文件名。
        return preifxTime + uuid + filename;
    }

}

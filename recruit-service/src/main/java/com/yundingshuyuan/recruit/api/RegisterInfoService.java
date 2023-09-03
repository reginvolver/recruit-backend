package com.yundingshuyuan.recruit.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface RegisterInfoService {


    /**
     * Description 提交应用程序照片
     *
     * @param file 文件
     * @param id
     * @return boolean
     * @author 李朋逊
     * @date 2023/08/02
     */
    public String submitApplicationPhoto(MultipartFile file, String id) throws FileNotFoundException;
}

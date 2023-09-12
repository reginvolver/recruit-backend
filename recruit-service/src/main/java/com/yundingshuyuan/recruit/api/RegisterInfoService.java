package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.vo.ApplicationPhotoVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

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
    String submitApplicationPhoto(MultipartFile file, String id) throws FileNotFoundException;

    List<ApplicationPhotoVo> downloadApplicationPhoto(String openId) throws FileNotFoundException;

    boolean deletePhoto(String openId) throws FileNotFoundException;
}

package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.vo.RegisterInfoVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface RegisterInfoService {
    /**
     * Description 保存注册信息
     *
     * @param registerInfoVO 注册信息签证官
     * @return boolean
     * @author 李朋逊
     * @date 2023/08/02
     */
    public boolean saveRegisterInfo(RegisterInfoVO registerInfoVO);

    /**
     * Description 更新注册信息
     *
     * @param registerInfoVO 注册信息签证官
     * @return boolean
     * @author 李朋逊
     * @date 2023/08/02
     */
    public boolean updateRegisterInfo(RegisterInfoVO registerInfoVO);


    /**
     * Description 提交应用程序照片
     *
     * @param file 文件
     * @param id
     * @return boolean
     * @author 李朋逊
     * @date 2023/08/02
     */
    public String submitApplicationPhoto(MultipartFile file, Integer id) throws FileNotFoundException;
}

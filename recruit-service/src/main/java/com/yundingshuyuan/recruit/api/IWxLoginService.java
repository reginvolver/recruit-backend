package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.vo.BasicResultVO;
import org.springframework.web.bind.annotation.RequestBody;

public interface IWxLoginService {
    public BasicResultVO<String> getOpenid(@RequestBody String data);
}

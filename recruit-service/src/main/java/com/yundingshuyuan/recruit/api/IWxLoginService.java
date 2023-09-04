package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.vo.BasicResultVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface IWxLoginService {
    public String getOpenid(@RequestBody String data);

    public Map<String,Integer> getLoginMes(String cloudId);
}

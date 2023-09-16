package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.UserInfo;
import com.yundingshuyuan.vo.BasicResultVO;
import org.springframework.web.bind.annotation.RequestBody;

public interface IWxLoginService {
    public BasicResultVO<String> wxLogin(@RequestBody String data);

    public UserInfo getLoginMes(String cloudId);
}

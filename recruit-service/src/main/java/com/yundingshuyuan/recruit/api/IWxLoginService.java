package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.UserInfo;
import com.yundingshuyuan.vo.BasicResultVO;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.RequestBody;

public interface IWxLoginService {
    public BasicResultVO<String> wxLogin(@RequestBody String data) throws WxErrorException;

    public UserInfo getLoginMes(String cloudId);
}

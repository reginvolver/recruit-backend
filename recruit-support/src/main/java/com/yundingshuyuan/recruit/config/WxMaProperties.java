package com.yundingshuyuan.recruit.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wx.miniapp")
public class WxMaProperties {

    @Value("${appid}")
    private String appid;

    @Value("${secret}")
    private String secret;

    private String token;

    private String aesKey;

    //@Value("${msgDataFormat}")
    private long msgDataFormat;


}
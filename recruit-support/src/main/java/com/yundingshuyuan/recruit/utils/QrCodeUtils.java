package com.yundingshuyuan.recruit.utils;


import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.http.HttpUtil;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.OutputStream;

/**
 * 二维码工具 (生成)<br>
 * 基于 hutool-QrCodeUtil 二次封装
 * <a href="https://apidoc.gitee.com/dromara/hutool/">api 文档</a>
 *
 * @author wys
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class QrCodeUtils {
    /**
     * 云顶书院logo base64
     */
    static final String LOGO_BASE64;
    /**
     * 云顶书院logo base64 url
     */
    static final String LOGO_BASE64_URL = "https://ruafafa-photobed.oss-cn-beijing.aliyuncs.com/logo_base64.txt";
    /**
     * 云顶书院 logo 主题色
     */
    static final Color LOGO_THEME_COLOR = Color.getHSBColor(0.5972f, 0.6522f, 0.7216f);
    /**
     * 最大信息承载
     */
    static final int MAX_VERSION = 40;

    static {
        LOGO_BASE64 = HttpUtil.get(LOGO_BASE64_URL);
    }

    /**
     * 生成默认的带书院 LOGO 的二维码
     *
     * @param content 二维码中内容
     * @return 二维码转 base64 编码
     */
    public String getQrCodeBase64(String content) {
        QrConfig config = new QrConfig(648, 648);
        config.setErrorCorrection(ErrorCorrectionLevel.H)
                .setMargin(1)
                .setForeColor(LOGO_THEME_COLOR)
                .setBackColor(Color.WHITE)
                .setRatio(4);
        // 生成二维码base64
        return QrCodeUtil.generateAsBase64(content, config, ImgUtil.IMAGE_TYPE_PNG, LOGO_BASE64);
    }

    /**
     * 生成带书院 LOGO 的二维码
     *
     * @param content   二维码中内容
     * @param width     宽度
     * @param height    高度
     * @param foreColor 前景色
     * @param backColor 背景色
     * @return 二维码转 base64 编码
     */
    public String getQrCodeBase64(String content, int width, int height, Color foreColor, Color backColor) {
        QrConfig config = new QrConfig(width, height);
        config.setErrorCorrection(ErrorCorrectionLevel.H)
                .setMargin(1)
                .setForeColor(foreColor)
                .setBackColor(backColor)
                .setRatio(4)
                .setQrVersion(calculateBestVersion(config, content));
        // 生成二维码base64
        return QrCodeUtil.generateAsBase64(content, config, ImgUtil.IMAGE_TYPE_PNG, LOGO_BASE64);
    }

    /**
     * 生成带书院 LOGO 的二维码
     *
     * @param content   二维码中内容
     * @param width     宽度
     * @param height    高度
     * @param foreColor 前景色
     * @param backColor 背景色
     * @return 二维码
     */
    public void getQrCode(String content, int width, int height, Color foreColor,
                          Color backColor, OutputStream outputStream) {
        QrConfig config = new QrConfig(width, height);
        config.setErrorCorrection(ErrorCorrectionLevel.H)
                .setMargin(1)
                .setForeColor(foreColor)
                .setBackColor(backColor)
                .setRatio(4)
                .setQrVersion(calculateBestVersion(config, content));
        // 生成二维码base64
        QrCodeUtil.generate(content, config, ImgUtil.IMAGE_TYPE_PNG, outputStream);
    }


    /**
     * 自定义生成书院二维码
     *
     * @param content 储存内容
     * @param config  自定义配置
     * @return
     */
    public String getQrCodeBase64(String content, QrConfig config) {
        return QrCodeUtil.generateAsBase64(content, config, ImgUtil.IMAGE_TYPE_PNG, LOGO_BASE64);
    }

    private int calculateBestVersion(QrConfig config, String content) {
        // 计算最佳二维码版本
        int version = 1;
        while (version <= MAX_VERSION) {
            config.setQrVersion(version);
            try {
                QrCodeUtil.generateAsBase64(content, config, ImgUtil.IMAGE_TYPE_PNG);
                return version;
            } catch (Exception ignored) {
            }
            version++;
        }
        return MAX_VERSION;
    }

}


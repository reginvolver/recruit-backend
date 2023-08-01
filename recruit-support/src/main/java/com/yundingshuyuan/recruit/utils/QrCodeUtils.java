package com.yundingshuyuan.recruit.utils;


import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.io.OutputStream;

/**
 * 二维码工具 (生成)<br>
 * 基于 hutool-QrCodeUtil 二次封装
 * <a href="https://apidoc.gitee.com/dromara/hutool/">api 文档</a>
 *
 * @author wys
 */
public class QrCodeUtils {
    /**
     * 云顶书院logo base64
     */
    static final String LOGO_BASE64 = "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";
    /**
     * 云顶书院 logo 主题色
     */
    static final Color LOGO_THEME_COLOR = Color.getHSBColor(0.5972f, 0.6522f, 0.7216f);

    /**
     * 生成默认的带书院 LOGO 的二维码
     *
     * @param content 二维码中内容
     * @return 二维码转 base64 编码
     */
    public static String getQrCodeBase64(String content) {
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
    public static String getQrCodeBase64(String content, int width, int height, Color foreColor, Color backColor) {
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
    public static void getQrCode(String content, int width, int height, Color foreColor,
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
    public static String getQrCodeBase64(String content, QrConfig config) {
        return QrCodeUtil.generateAsBase64(content, config, ImgUtil.IMAGE_TYPE_PNG, LOGO_BASE64);
    }

    private static int calculateBestVersion(QrConfig config, String content) {
        // 计算最佳二维码版本
        int version = 1;
        while (version <= 40) {
            config.setQrVersion(version);
            try {
                QrCodeUtil.generateAsBase64(content, config, ImgUtil.IMAGE_TYPE_PNG);
                return version;
            } catch (Exception e) {
            }
            version++;
        }
        return 40;
    }

}


package com.yundingshuyuan.ObjectHandler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * 元对象处理程序
 * 进行公共字段填充(createTime,updateTime等)：
 *
 * @author 李朋逊
 * @date 2023/07/24
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
@Override
    public void insertFill(MetaObject metaObject) {
    log.info("公共字段自动填充[insert]");
    metaObject.setValue("createTime", LocalDateTime.now());
    metaObject.setValue("updateTime",LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]");
        log.info(metaObject.toString());
        metaObject.setValue("updateTime",LocalDateTime.now());
    }
}

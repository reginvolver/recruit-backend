package com.yundingshuyuan.recruit.web.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yundingshuyuan.recruit.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 监听 Excel 解析
 */
@Slf4j
@Component
public class TestExcelReadListener extends AnalysisEventListener<User> {

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
    }

    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        // ...
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // ...
    }
}

package com.yundingshuyuan.recruit.service;

import com.mzt.logapi.beans.LogRecord;
import com.mzt.logapi.service.ILogRecordService;
import com.yundingshuyuan.recruit.dao.LogRecordMapper;
import com.yundingshuyuan.recruit.domain.po.LogRecordPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * bizlog日志落库实现类
 */
@Service
@Slf4j
public class DbLogRecordServiceImpl implements ILogRecordService {

    @Resource
    private LogRecordMapper logRecordMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void record(LogRecord logRecord) {
        log.info(String.valueOf(logRecord));
        LogRecordPO logRecordPO = LogRecordPO.toPo(logRecord);
        logRecordMapper.insert(logRecordPO);
    }

    @Override
    public List<LogRecord> queryLog(String bizNo, String type) {
        return null;
    }

    @Override
    public List<LogRecord> queryLogByBizNo(String bizNo, String type, String subType) {
        return null;
    }
}
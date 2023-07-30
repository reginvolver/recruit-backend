package com.yundingshuyuan.recruit.service;

import com.yundingshuyuan.recruit.api.InterviewerService;
import com.yundingshuyuan.recruit.dao.InterviewerMapper;
import com.yundingshuyuan.recruit.domain.InterviewerInfo;
import com.yundingshuyuan.recruit.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InterviewerServiceImpl implements InterviewerService {
    //excel文件地址
    private String fileName = "D:\\interviewer.xlsx";
    private InterviewerInfo interviewerInfo = new InterviewerInfo();
    @Autowired
    private InterviewerMapper interMapper;

    public void excel() throws FileNotFoundException {
        //读取excel
        InputStream inputStream = new FileInputStream(fileName);
        List<InterviewerInfo> interviewerlist = ExcelUtils.readOneExcel(interviewerInfo, inputStream);
        System.out.println(interviewerlist);
        //流处理填充interviewerInfo对象的username,password
        List<InterviewerInfo> infos = interviewerlist.stream().map((item) -> {
            Integer username = generateCode(item.getGroupId(), item.getFirstInterName());
            Integer password = generateCode(item.getGroupId(), item.getSecondInterName());
            item.setUsername(username.toString());
            item.setPassword(password.toString());
            return item;
        }).collect(Collectors.toList());
        log.info("{}", infos);
        //将数据写入数据库
        interMapper.insertBatch(infos);
        //填充excel文件
        OutputStream outputStream = new FileOutputStream(fileName);
        ExcelUtils.writeOneSheet(infos, "", outputStream);

    }

    //用户名 密码生成策略
    @Override
    public Integer generateCode(Integer id, String name) {
        Integer integer = id + "云昭云曜".hashCode() + name.hashCode();
        return integer;
    }
}

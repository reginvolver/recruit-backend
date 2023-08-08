package com.yundingshuyuan.recruit.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import com.yundingshuyuan.recruit.api.AuditService;
import com.yundingshuyuan.recruit.dao.AuditResultMapper;
import com.yundingshuyuan.recruit.domain.po.AuditResultPo;
import com.yundingshuyuan.recruit.domain.vo.AuditResultVo;
import com.yundingshuyuan.recruit.domain.vo.OutComeAmount;
import com.yundingshuyuan.recruit.utils.ExcelUtils;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private static final String KEYWORD_INTERVIEWEE = "user_name";
    private static final String KEYWORD_GROUP_ID = "group_id";
    private static final String KEYWORD_STUDENT_ID = "student_number";

    private final AuditResultMapper arMapper;
    private final Converter converter;

    @Override
    public List<AuditResultVo> getAllResult() {
        return listConverter(arMapper.getAllResult());
    }

    @Override
    public List<AuditResultVo> getResultByKeyword(String keyword, String search) {
        switch (keyword) {
            case "interviewee":
                keyword = KEYWORD_INTERVIEWEE;
                break;
            case "groupId":
                keyword = KEYWORD_GROUP_ID;
                break;
            case "studentId":
                keyword = KEYWORD_STUDENT_ID;
                break;
            default:
                throw new InvalidParameterException("没有该关键词");
        }
        return listConverter(arMapper.getResultByKeyword(keyword, search));
    }

    @Override
    public List<AuditResultVo> getPassedResultByPassState(boolean isPassed) {
        return listConverter(arMapper.getPassedResultByPassState(isPassed));
    }

    @Override
    public OutComeAmount getOutcomeAmount() {
        Long resultAmount = arMapper.getResultAmount();
        Long passedResultAmount = arMapper.getPassedResultAmount();
        Long notPassedResultAmount = arMapper.getNotPassedResultAmount();
        return new OutComeAmount(resultAmount, passedResultAmount, notPassedResultAmount);
    }

    @Override
    public void getAllResultExportToExcel(HttpServletResponse response, String fileName) throws IOException {
        response.setContentType(ContentType.MULTIPART.getValue());
        response.setHeader(Header.CONTENT_DISPOSITION.getValue(),
                StrUtil.format("attachment;filename={}.xlsx", fileName));
        ExcelUtils.writeOneSheet(getAllResult(), "recruit_result", response.getOutputStream());
    }

    @Override
    public int changePassedResultBySid(long studentId) {
        return arMapper.changePassedResultBySid(studentId);
    }

    @Override
    public int changePassedResultByUid(long userId) {
        return arMapper.changePassedResultByUid(userId);
    }

    @Override
    public int changeAllPassed() {
        return arMapper.changeAllPassed();
    }

    /**
     * list PO to VO
     *
     * @param list List of Po
     * @return List of Vo
     */
    private List<AuditResultVo> listConverter(List<AuditResultPo> list) {
        return list.stream().map(o -> converter.convert(o, AuditResultVo.class))
                .collect(Collectors.toList());
    }

}

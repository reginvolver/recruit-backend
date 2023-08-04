package com.yundingshuyuan.recruit.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yundingshuyuan.recruit.dao.LectureMapper;
import com.yundingshuyuan.recruit.domain.Lecture;
import com.yundingshuyuan.recruit.domain.vo.LectureVo;
import com.yundingshuyuan.recruit.service.ILectureService;
import com.yundingshuyuan.vo.BasicResultVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LectureServiceImpl extends ServiceImpl<LectureMapper, Lecture> implements ILectureService {

    /**
     * 发布宣讲会，将前端传的数据存储在数据库中
     *
     * @param lecture
     * @return
     */
    public BasicResultVO release(Lecture lecture) {
        if (isBlank(lecture)) {
            return BasicResultVO.fail("参数不能为空");
        } else if (isExist(lecture)) {
            return BasicResultVO.fail("该场宣讲会已存在");
        } else if (equalOrder(lecture)) {
            return BasicResultVO.fail("宣讲会排序重复");
        }

        saveOrUpdate(lecture);

        BasicResultVO basicResultVO = showAll();

        return basicResultVO;
    }

    /**
     * 返回所有宣讲会的信息
     *
     * @return
     */
    @Override
    public BasicResultVO showAll() {
        //查询出数据库中的所有信息
        List<Lecture> lectures = list();
        ArrayList<LectureVo> lectureVos = new ArrayList<>();
        for (Lecture lecture : lectures) {
            LectureVo lectureVo = new LectureVo();
            BeanUtil.copyProperties(lecture, lectureVo, true);
            lectureVos.add(lectureVo);
        }
        if (lectureVos.size() == 0) {
            return BasicResultVO.fail("暂无宣讲会记录");
        }
        return BasicResultVO.success(lectureVos);
    }

    /**
     * 查看宣讲会的排序是否重复
     *
     * @param lecture
     * @return
     */
    private boolean equalOrder(Lecture lecture) {
        QueryWrapper<Lecture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lecture_order", lecture.getLectureOrder());
        Lecture one = getOne(queryWrapper);
        if (one != null) {
            return true;
        }
        return false;
    }


    /**
     * 判断前端传的lecture是否在数据库中已存在
     *
     * @param lecture
     * @return
     */
    private boolean isExist(Lecture lecture) {
        QueryWrapper<Lecture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("speaker", lecture.getSpeaker());
        queryWrapper.eq("lecture_theme", lecture.getLectureTheme());
        queryWrapper.eq("lecture_time", lecture.getLectureTime());
        queryWrapper.eq("ticket_number", lecture.getTicketNumber());
        queryWrapper.eq("ticket_remain", lecture.getTicketRemain());
        queryWrapper.eq("lecture_order", lecture.getLectureOrder());
        Lecture one = getOne(queryWrapper);
        if (one != null) {
            return true;
        }
        return false;
    }

    /**
     * 判断前端传的lecture中各项数据是否为空
     *
     * @param lecture
     * @return
     */
    private boolean isBlank(Lecture lecture) {
        String speaker = lecture.getSpeaker();
        String lectureTheme = lecture.getLectureTheme();
        String lectureTime = String.valueOf(lecture.getLectureTime());
        String lectureOrder = String.valueOf(lecture.getLectureOrder());
        String ticketNumber = String.valueOf(lecture.getTicketNumber());
        String ticketRemain = String.valueOf(lecture.getTicketRemain());
        if (StrUtil.isBlank(speaker) || StrUtil.isBlank(lectureTheme)
                || StrUtil.isBlank(lectureTime) || StrUtil.isBlank(lectureOrder)
                || StrUtil.isBlank(ticketNumber) || StrUtil.isBlank(ticketRemain)) {
            return true;
        }
        return false;
    }
}

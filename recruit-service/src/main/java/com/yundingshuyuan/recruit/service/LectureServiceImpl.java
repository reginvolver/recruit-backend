package com.yundingshuyuan.recruit.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yundingshuyuan.recruit.api.ILectureService;
import com.yundingshuyuan.recruit.dao.LectureMapper;
import com.yundingshuyuan.recruit.domain.Lecture;
import com.yundingshuyuan.recruit.domain.vo.LectureVo;

import com.yundingshuyuan.vo.BasicResultVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LectureServiceImpl extends ServiceImpl<LectureMapper, Lecture> implements ILectureService {

    /**
     * 发布宣讲会，将前端传的数据存储在数据库中
     *
     * @param lecture
     * @return
     */
    public BasicResultVO<ArrayList<LectureVo>> release(Lecture lecture) {
        if (Objects.isNull(lecture)) {
            return BasicResultVO.fail("参数不能为空");
        } else if (isExist(lecture)) {
            return BasicResultVO.fail("该场宣讲会已存在");
        } else if (equalOrder(lecture)) {
            return BasicResultVO.fail("宣讲会排序重复");
        }

        saveOrUpdate(lecture);

        return showAll();
    }

    /**
     * 返回所有宣讲会的信息
     *
     * @return
     */
    @Override
    public BasicResultVO<ArrayList<LectureVo>> showAll() {
        //查询出数据库中的所有信息
        List<Lecture> lectures = list();
        ArrayList<LectureVo> lectureVos = new ArrayList<>();
        for (Lecture lecture : lectures) {
            LectureVo lectureVo = new LectureVo();
            BeanUtil.copyProperties(lecture, lectureVo, true);
            lectureVos.add(lectureVo);
        }
        if (lectureVos.isEmpty()) {
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
        Lecture one = getOne(new QueryWrapper<>(lecture));
        if (one != null) {
            return true;
        }
        return false;
    }


}

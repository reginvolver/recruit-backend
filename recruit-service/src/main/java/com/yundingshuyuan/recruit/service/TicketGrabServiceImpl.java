package com.yundingshuyuan.recruit.service;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.yundingshuyuan.recruit.api.TicketGrabService;
import com.yundingshuyuan.recruit.dao.LectureMapper;
import com.yundingshuyuan.recruit.dao.LectureTicketMapper;
import com.yundingshuyuan.recruit.dao.UserInfoMapper;
import com.yundingshuyuan.recruit.domain.LectureTicket;
import com.yundingshuyuan.recruit.domain.vo.LectureTicketVo;
import com.yundingshuyuan.recruit.domain.vo.LectureVo;
import com.yundingshuyuan.recruit.utils.QrCodeUtils;
import com.yundingshuyuan.recruit.utils.RedisUtils;
import io.github.linpeilie.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author cr
 * @Date 2023/8/3 12:58
 */
@Slf4j
@Service
public class TicketGrabServiceImpl implements TicketGrabService {

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    LectureMapper lectureMapper;
    @Autowired
    LectureTicketMapper lectureTicketMapper;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    QrCodeUtils qrCodeUtils;
    @Autowired
    Converter converter;
    @Resource
    private ThreadPoolExecutor dtpExecutor1;


    /**
     * 判断这个人是否抢到票
     *
     * @return
     */
    @Override
    public boolean checkRecordExists(Integer userId) {
        Integer count = lectureTicketMapper.checkCount(userId,showLeastLecture().getLectureId());
        if (count < 0){
            return false;
        }
        return true;
    }

    /**
     * 获取最新的宣讲会数据库的信息
     */
    @Override
    public LectureVo showLeastLecture() {
        return lectureMapper.theLeast();
    }

    /**
     * 抢票并生成二维码
     */
    @Override
    @Transactional
    public LectureTicketVo ticketGrab(Integer ticketId, Integer userId) {

        LectureTicketVo  lectureTicketVo = new LectureTicketVo();
        validateExist(ticketId,userId);
        String key = "ticket:count" + ticketId;
        Integer remainCount = (Integer) redisUtils.get(key);
        if (remainCount < 1){
           return null;
        }
        long remain  = redisUtils.decr(key,1);
        if (remain < 0){
            redisUtils.incr(key,1);
            return null;
        }

        String s = generateCode(ticketId, userId);

        lectureTicketVo.setCode(s);
        lectureTicketVo.setLectureId(ticketId);
        lectureTicketVo.setUserId(userId);
        redisUtils.set("grab:"+userId+":"+ticketId,lectureTicketVo);
        return lectureTicketVo;
    }

    /**
     * 生成二维码
     */
    @Override
    public String generateCode(Integer ticketId, Integer userId) {
        //content
        JSONObject data = new JSONObject();
        data.put("userId",userId);
        data.put("ticketId",ticketId);
        String content = data.toJSONString();
        return qrCodeUtils.getQrCodeBase64(content);
    }

    /**
     * 返回用户的宣讲会二维码
     *
     * @param userId
     * @return
     */
    @Override
    public String userQRCode(Integer userId) {
        LectureTicketVo lectureTicketVo = lectureTicketMapper.selectByUserId(userId, showLeastLecture().getLectureId());
        if (ObjectUtil.isEmpty(lectureTicketVo)) {
            String key = "grab:" + userId;
            Object o = redisUtils.get(key);
            LectureTicketVo convert = converter.convert(o, LectureTicketVo.class);
            return convert.getCode();
        }
        return lectureTicketVo.getCode();
    }


    /**
     * 扫码得到用户宣讲会信息,修改isLecture字段
     *
     * @param lectureTicketVo
     */
    @Override
    public void scanTicketInfo(LectureTicketVo lectureTicketVo) {
        validateScan(lectureTicketVo);
        userInfoMapper.incrCount(lectureTicketVo.getUserId());
    }



    /**
     * 将redis中的数据持久化到mysql中
     */
    @Override
    @Scheduled(fixedDelay = 600000)    //1分钟
    public void redisToMysql() {
        //System.currentTimeMillis() - Long.parseLong(requestTime)
        long start = System.currentTimeMillis();
        log.info("定时任务开始执行");
        List<LectureTicket> list = new ArrayList<>();
        String prefix = "grab:";
        Set<String> keys = redisUtils.keys(prefix);
        for (String key : keys
             ) {
            Object object = redisUtils.get(key);
            LectureTicket convert = converter.convert(object, LectureTicket.class);
            list.add(convert);
        }
        lectureTicketMapper.insertBatch(list);
        long end = System.currentTimeMillis();
        log.info("定时任务执行完毕，用时：" + (end-start) + "毫秒");
    }

    public void validateScan(LectureTicketVo lectureTicketVo) {
        if (lectureTicketVo.getStatus() == 1){
            throw new InvalidParameterException("该二维码已经扫描过了，请勿重复扫描");
        }
        if (lectureTicketVo.getLectureId().equals(showLeastLecture().getLectureId())){
            throw new InvalidParameterException("该二维码不是最新场次，请检查二维码是否错误");
        }
    }

    public void validateExist(Integer ticketId,Integer userId){
        Integer isExist = lectureTicketMapper.checkCount(userId, ticketId);
        if (isExist > 0){
            throw new InvalidParameterException("一位用户不可重复抢票");
        }
    }


}

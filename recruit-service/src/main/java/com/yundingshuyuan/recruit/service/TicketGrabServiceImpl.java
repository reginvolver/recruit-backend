package com.yundingshuyuan.recruit.service;

import com.yundingshuyuan.constant.CommonConstant;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    @Resource
    QrCodeUtils qrCodeUtils;
    @Autowired
    Converter converter;
    @Resource
    StringRedisTemplate stringRedisTemplate;


    /**
     * 根据lectureId查找对应的宣讲会信息
     *
     * @param lectureId
     * @return
     */
    @Override
    public LectureVo detailLecture(Integer lectureId) {
        return lectureMapper.lectureById(lectureId);
    }

    /**
     * 用户所有已经抢到的票
     *
     * @param userId
     * @return
     */
    @Override
    public List<LectureVo> allTicketByUser(Integer userId) {
        List<Integer> lectureIds = lectureTicketMapper.allTicketsByUserId(userId);
        List<LectureVo> lectureVos = new ArrayList<>();
        for (Integer e : lectureIds) {

            lectureVos.add(lectureMapper.lectureById(e));
        }
        return lectureVos;
    }



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
    public boolean ticketGrab(Integer ticketId, Integer userId) {

        LectureTicketVo  lectureTicketVo = new LectureTicketVo();
        validateExist(ticketId,userId);
        String key = "ticket:count" + ticketId;
        String garbTimeKey = "grabtime:count" + ticketId;
        Integer remainCount = Integer.valueOf(stringRedisTemplate.opsForValue().get(key));
        // 时间
        String grabTimeStr =  stringRedisTemplate.opsForValue().get(garbTimeKey);
        log.info("宣讲会{} 抢票时{}", ticketId, grabTimeStr);
        long grabTimeMills = LocalDateTime.parse(grabTimeStr,
                DateTimeFormatter.ofPattern(CommonConstant.DATE_TIME_FORMAT_YMDHMS))
                    .toInstant(ZoneOffset.UTC).toEpochMilli();
        //
        if(System.currentTimeMillis() <  grabTimeMills - 5000){
            log.info(" 用户id{} : 未到开启抢票时间宣讲会 {} ", userId, ticketId);
            throw new RuntimeException("还未开启抢票");
        }

        if (remainCount == null){
            throw new RuntimeException();
        }
        if (remainCount < 1){
            log.info("userId为：     " + userId + "的用户没抢到     "+ ticketId + "场宣讲会的票，剩余票数"+ remainCount);
           return false;
        }
        long remain  = stringRedisTemplate.opsForValue().increment(key,-1);
        if (remain < 0){
            stringRedisTemplate.opsForValue().increment(key,1);
            log.info("userId为：     " + userId + "的用户没抢到     "+ ticketId + "场宣讲会的票，剩余票数"+ remainCount);
            return false;
        }

        lectureTicketVo.setLectureId(ticketId);
        lectureTicketVo.setUserId(userId);
        stringRedisTemplate.opsForValue().setIfAbsent("grab:"+userId+":"+ticketId,"ok",120, TimeUnit.SECONDS);
        log.info("userId为     ：" + userId + "的用户抢到了第     "+ ticketId + "场宣讲会的票，剩余票数"+ (remainCount-1));
        return true;
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
     * 将redis中的数据持久化到mysql中,并删除已经持久化过的数据
     */
    @Override
    @Scheduled(fixedDelay = 60000)    //1分钟
    public void redisToMysql() {
        //System.currentTimeMillis() - Long.parseLong(requestTime)
        long start = System.currentTimeMillis();
        log.info("定时任务开始执行");
        String prefix = "*grab:";
        Set<String> keys = stringRedisTemplate.keys(prefix+"*");
        for (String key : keys
             ) {
            LectureTicket lectureTicketVo = new LectureTicket();
            String[] split = key.split(":");
            String userId = split[1];
            String lectureId = split[2];
            lectureTicketVo.setLectureId(Integer.valueOf(lectureId));
            lectureTicketVo.setUserId(Integer.valueOf(userId));
            lectureTicketMapper.insert(lectureTicketVo);
            stringRedisTemplate.delete("grab:" + userId + ":" + lectureId);
        }

        long end = System.currentTimeMillis();
        log.info("定时任务执行完毕，用时：" + (end-start) + "毫秒");
    }

    /**
     * 获取所有宣讲会信息
     *
     * @return
     */
    @Override
    public List<LectureVo> allLecture() {
        return lectureMapper.allLecture();
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
        // redis exist || mysql exist
        if ((stringRedisTemplate.opsForValue().get("grab:"+userId+":"+ticketId) != null)
                || lectureTicketMapper.checkCount(userId, ticketId) > 0){
            throw new InvalidParameterException("一位用户不可重复抢票");
        }
    }


}

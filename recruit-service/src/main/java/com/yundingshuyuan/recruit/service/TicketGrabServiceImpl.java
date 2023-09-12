package com.yundingshuyuan.recruit.service;

import com.yundingshuyuan.constant.CommonConstant;
import com.yundingshuyuan.constant.RedisConstant;
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
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    @Autowired
    RedissonClient redissonClient;


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
        List<LectureVo> lectureVos = new ArrayList<>();
        // 快速查看开启
        Set<String> keys = quickLectureTicketView(userId);
        if (keys != null) {
            // 未持久化前返回一个只有lectureId的票demo
            for (String key : keys) {
                LectureVo lectureVo = new LectureVo();
                String[] split = key.split(":");
                Integer lectureId = Integer.valueOf(split[2]);
                lectureVo.setLectureId(lectureId);
                lectureVo.setLectureTheme("已抢到票！请稍等(查看详细)");
                lectureVo.setSpeaker("等待加载...");
                lectureVo.setContentIntroduction("恭喜你已经抢到宣讲会门票(^-^)!，数据录入可能有延迟" +
                        "，过一段时间后刷新，请耐心等待全部数据加载，门票已生效");
                lectureVos.add(lectureVo);
            }
        } else {
            // 持久化后mysql获取
            List<Integer> lectureIds = lectureTicketMapper.allTicketsByUserId(userId);
            for (Integer e : lectureIds) {
                lectureVos.add(lectureMapper.lectureById(e));
            }
        }
        return lectureVos;
    }

    /**
     * 快速查看用户所有已经抢到的票
     *
     * @param userId
     * @return
     */
    private Set<String> quickLectureTicketView(Integer userId) {
        Set<String> keys = stringRedisTemplate.keys("grab:" + userId + ":*");
        if (keys != null && !keys.isEmpty()) {
            return keys;
        }
        return null;
    }


    /**
     * 判断这个人是否抢到票
     *
     * @return
     */
    @Override
    public boolean checkRecordExists(Integer userId) {
        Integer count = lectureTicketMapper.checkCount(userId, showLeastLecture().getLectureId());
        return count >= 0;
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
    @Transactional(rollbackFor = {RuntimeException.class, InvalidParameterException.class})
    public boolean ticketGrab(Integer ticketId, Integer userId) {
        String garbTicketLock = RedisConstant.GRAB_TICKET_LOCK + userId;
        // 获取锁 + 加锁
        RLock lock = redissonClient.getLock(garbTicketLock);
        boolean isNoLocked = lock.tryLock();
        if (isNoLocked) {
            log.info("进程{},{}",userId, ticketId);
            // 业务实现
            try {
                // 1. 资格检验
                validateQualification(ticketId, userId);
                // 2. 抢票
                grabTicket(ticketId, userId);
                return true;
            } finally {
                lock.unlock();
            }
        } else {
            return false;
        }
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
        Set<String> keys = stringRedisTemplate.keys(prefix + "*");
        for (String key : keys) {
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
        log.info("定时任务执行完毕，用时：" + (end - start) + "毫秒");
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
        if (lectureTicketVo.getStatus() == 1) {
            throw new InvalidParameterException("该二维码已经扫描过了，请勿重复扫描");
        }
        if (lectureTicketVo.getLectureId().equals(showLeastLecture().getLectureId())) {
            throw new InvalidParameterException("该二维码不是最新场次，请检查二维码是否错误");
        }
    }

    /**
     * 校验是否抢过票
     *
     * @param ticketId
     * @param userId
     */
    public void validateExist(Integer ticketId, Integer userId) {
        log.info("Exist");
        // redis exist || mysql exist
        if ((stringRedisTemplate.opsForValue().get("grab:" + userId + ":" + ticketId) != null)
                || lectureTicketMapper.checkCount(userId, ticketId) > 0) {
            throw new InvalidParameterException("一位用户不可重复抢票");
        }
    }

    /**
     * 校验是否到达抢票时间
     *
     * @param ticketId
     */
    public void validateOpenTime(Integer ticketId) {
        String garbTimeKey = RedisConstant.GRAB_TIME_PREFIX + ticketId;
        // 时间
        String grabTimeStr = stringRedisTemplate.opsForValue().get(garbTimeKey);
        LocalDateTime grabTime = LocalDateTime.parse(grabTimeStr, DateTimeFormatter.ofPattern(CommonConstant.DATE_TIME_FORMAT_YMDHMS));
        LocalDateTime nowTime = LocalDateTime.now();
        // 获取两个 LocalDateTime 对象的毫秒表示
        long grabT = grabTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
        long nowT = nowTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
        log.info("宣讲会{} 抢票时{} 当前时间{}", ticketId, grabTime, nowTime);
        // 计算时间差
        if (nowT < grabT - 5000) {
            throw new RuntimeException("还未开启抢票");
        }
    }

    /**
     * 校验是否有剩余票
     *
     * @param ticketId
     * @param userId
     */
    public void validateResidue(Integer ticketId, Integer userId) {
        log.info("Residue");
        // 设置key
        String lectureTicketKey = RedisConstant.LECTURE_TICKET_PREFIX + ticketId;
        int remainNum = Integer.parseInt(Objects.requireNonNull(stringRedisTemplate.opsForValue().get(lectureTicketKey)));
        RAtomicLong atomicLong = redissonClient.getAtomicLong(lectureTicketKey);
        if (remainNum < 1) {
            // 恢复库存并释放锁
            log.info("userId为：" + userId + " 的用户没抢到     " + ticketId + "场宣讲会的票，剩余票数" + remainNum);
            log.info("呜呜~票已经抢完了！");
            throw new IndexOutOfBoundsException("票剩余0");
        }
        if (remainNum == -1) {
            atomicLong.incrementAndGet();
        }
    }

    /**
     * 校验抢票资格
     *
     * @param ticketId
     * @param userId
     */
    public void validateQualification(Integer ticketId, Integer userId) {
        // 是否到达抢票时间
        validateOpenTime(ticketId);
        // 是否有余票
        validateResidue(ticketId, userId);
        // 不可重复抢票
        validateExist(ticketId, userId);
    }

    /**
     * 抢票操作
     *
     * @param ticketId
     * @param userId
     */
    public void grabTicket(Integer ticketId, Integer userId) {
        // 设置key
        String lectureTicketkey = RedisConstant.LECTURE_TICKET_PREFIX + ticketId;
        RAtomicLong atomicLong = redissonClient.getAtomicLong(lectureTicketkey);
        long remain = atomicLong.decrementAndGet();
        // 插入key
        stringRedisTemplate.opsForValue().setIfAbsent(RedisConstant.GRAB_USER_RECORD + userId + ":" + ticketId,
                "ok", 120, TimeUnit.SECONDS);
        log.info("userId为     ：" + userId + "的用户抢到了第     " + ticketId + "场宣讲会的票，剩余票数" + remain);
    }


}

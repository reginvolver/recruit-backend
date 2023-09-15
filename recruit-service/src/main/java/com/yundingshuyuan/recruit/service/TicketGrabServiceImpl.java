package com.yundingshuyuan.recruit.service;

import cn.hutool.core.util.StrUtil;
import com.yundingshuyuan.constant.CommonConstant;
import com.yundingshuyuan.constant.RedisConstant;
import com.yundingshuyuan.enums.TicketGrabRespStatusEnum;
import com.yundingshuyuan.recruit.api.TicketGrabService;
import com.yundingshuyuan.recruit.dao.LectureMapper;
import com.yundingshuyuan.recruit.dao.LectureTicketMapper;
import com.yundingshuyuan.recruit.dao.UserInfoMapper;
import com.yundingshuyuan.recruit.domain.LectureTicket;
import com.yundingshuyuan.recruit.domain.vo.LectureTicketVo;
import com.yundingshuyuan.recruit.domain.vo.LectureVo;
import com.yundingshuyuan.recruit.utils.QrCodeUtils;
import com.yundingshuyuan.recruit.utils.RedisUtils;
import com.yundingshuyuan.vo.BasicResultVO;
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
import java.util.Collections;
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
        // 快速查看开启
        List<LectureVo> quickViewTickets = quickLectureTicketView(userId);
        List<LectureVo> lectureResultVos = new ArrayList<>(quickViewTickets);
        // 较慢记录获取
        List<Integer> lectureIds = lectureTicketMapper.allTicketsByUserId(userId);
        lectureIds.forEach(o -> lectureResultVos.add(lectureMapper.lectureById(o)));
        return lectureResultVos;
    }

    /**
     * 快速查看用户所有已经抢到的票
     *
     * @param userId
     * @return
     */
    private List<LectureVo> quickLectureTicketView(Integer userId) {
        // 结果表
        List<LectureVo> lectureVos = new ArrayList<>();
        Set<String> keys = stringRedisTemplate.keys(RedisConstant.GRAB_USER_RECORD + userId + ":*");
        if (keys != null && !keys.isEmpty()) {
            // 未存入库中前返回一个只有lectureId的票demo
            for (String key : keys) {
                String[] split = key.split(":");
                Integer lectureId = Integer.valueOf(split[2]);
                LectureVo.LectureVoBuilder builder = LectureVo.builder();
                LectureVo lectureVo = builder.lectureId(lectureId)
                        .lectureTheme("已抢到票！请稍等(加载中...)")
                        .speaker("等待加载...")
                        .contentIntroduction("恭喜你已经抢到宣讲会门票(^-^)!，数据录入可能有延迟" +
                                "，过一段时间后刷新，请耐心等待全部数据加载，门票已生效").build();
                lectureVos.add(lectureVo);
                return lectureVos;
            }
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
    public BasicResultVO<Boolean> ticketGrab(Integer ticketId, Integer userId) {
        // 限制单个用户
        String garbTicketLock = RedisConstant.GRAB_TICKET_LOCK + userId;
        // 获取锁 + 加锁 (锁住校验抢票和添加到redis的逻辑)
        RLock lock = redissonClient.getLock(garbTicketLock);
        if (lock.tryLock()) {
            // 业务实现
            try {
                // 1. 资格检验
                BasicResultVO<Boolean> obstacle = validateQualification(ticketId, userId);
                if (obstacle == null) {
                    // 2. 抢票
                    // 获取 Key-> value 修改
                    String lectureTicketKey = RedisConstant.LECTURE_TICKET_PREFIX + ticketId;
                    RAtomicLong atomicLong = redissonClient.getAtomicLong(lectureTicketKey);
                    // 乐观,允许抢票->校验remain
                    long remainAfterOperate = atomicLong.decrementAndGet();
                    // 超卖恢复 限制多个用户
                    if (remainAfterOperate < 0) {
                        // 恢复库存
                        atomicLong.set(0);
                        // 打印
                        log.info("非常遗憾！user {} 同学没有抢到票！( º﹃º )", userId);
                        return new BasicResultVO<>(TicketGrabRespStatusEnum.NOT_GET_TICKET);
                    }
                    log.info("恭喜！user {} 同学抢到了 第{}场宣讲会 的门票(*´▽`*)，还剩票数{}张..", userId, ticketId, remainAfterOperate);
                    // 插入key
                    stringRedisTemplate.opsForValue().setIfAbsent(RedisConstant.GRAB_USER_RECORD + userId + ":" + ticketId,
                            "ok", 120, TimeUnit.SECONDS);
                    return BasicResultVO.success(StrUtil.format(TicketGrabRespStatusEnum.GET_TICKET.getMsg(), ticketId));
                } else {
                    return obstacle;
                }
            } finally {
                lock.unlock();
            }
        }
        log.error(TicketGrabRespStatusEnum.FREQUENT_REQUEST.getMsg());
        return new BasicResultVO<>(TicketGrabRespStatusEnum.FREQUENT_REQUEST);
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
     * @return
     */
    public boolean isExistTicket(Integer ticketId, Integer userId) {
        // redis exist || mysql exist
        if ((stringRedisTemplate.opsForValue().get(RedisConstant.GRAB_USER_RECORD + userId + ":" + ticketId) != null)
                || lectureTicketMapper.checkCount(userId, ticketId) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 校验是否到达抢票时间
     *
     * @param ticketId
     */
    public boolean isReachOpenTime(Integer ticketId) {
        String garbTimeKey = RedisConstant.GRAB_TIME_PREFIX + ticketId;
        // 时间
        String grabTimeStr = stringRedisTemplate.opsForValue().get(garbTimeKey);
        LocalDateTime grabTime = LocalDateTime.parse(grabTimeStr, DateTimeFormatter.ofPattern(CommonConstant.DATE_TIME_FORMAT_YMDHMS));
        LocalDateTime nowTime = LocalDateTime.now();
        // 获取两个 LocalDateTime 对象的毫秒表示
        long grabT = grabTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
        long nowT = nowTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
        // 计算时间差
        if (nowT < grabT - 5000) {
            return false;
        }
        return true;
    }

    /**
     * 校验是否有余票？
     *
     * @param ticketId
     * @return
     */
    private boolean isNoSurplus(Integer ticketId) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(RedisConstant.LECTURE_TICKET_PREFIX + ticketId);
        long l = atomicLong.get();
        System.out.println(l + "?");
        if (l <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 校验抢票资格
     *
     * @param ticketId
     */
    public BasicResultVO<Boolean> validateQualification(Integer ticketId, Integer userId) {
        // 是否到达抢票时间
        if (!isReachOpenTime(ticketId)) {
            return new BasicResultVO<>(TicketGrabRespStatusEnum.TIME_NOT_ALLOW.getCode(),
                    TicketGrabRespStatusEnum.TIME_NOT_ALLOW.getMsg());
        }
        // 不可重复抢票校验
        if (isExistTicket(ticketId, userId)) {
            return new BasicResultVO<>(TicketGrabRespStatusEnum.TICKETS_ALREADY_AVAILABLE);
        }
        // 票售空检验
        if (isNoSurplus(ticketId)) {
            return new BasicResultVO<>(TicketGrabRespStatusEnum.NO_TICKET_SURPLUS);
        }
        return null;
    }


}

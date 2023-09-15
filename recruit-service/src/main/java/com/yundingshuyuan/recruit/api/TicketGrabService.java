package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.vo.LectureTicketVo;
import com.yundingshuyuan.recruit.domain.vo.LectureVo;
import com.yundingshuyuan.vo.BasicResultVO;

import java.util.List;

/**
 * @Author cr
 * @Date 2023/8/3 12:58
 */
public interface TicketGrabService {

    /**
     * 根据lectureId查找对应的宣讲会信息
     *
     * @param lectureId
     * @return
     */
    public LectureVo detailLecture(Integer lectureId);


    /**
     * 用户所有已经抢到的票
     *
     * @param userId
     * @return
     */
    public List<LectureVo> allTicketByUser(Integer userId);


    /**
     * 判断这个人是否已经抢到票
     *
     * @param userId
     * @return
     */
    public boolean checkRecordExists(Integer userId);
    /**
     * 获取最新的数据库的信息
     */
    public LectureVo showLeastLecture();

    /**
     * 抢票并生成二维码
     */
    public BasicResultVO<Boolean> ticketGrab(Integer ticketId, Integer userId);

//    /**
//     * 生成二维码
//     */
//    public String generateCode(Integer ticketId,Integer userId);


    /**
     * 扫码得到用户已经宣讲会信息
     *
     * @param lectureTicketVo
     */
    public void scanTicketInfo(LectureTicketVo lectureTicketVo);

    /**
     * 将redis中的数据持久化到mysql中
     */
    public void redisToMysql();

    /**
     *
     * 获取所有宣讲会信息
     * @return
     */
    public List<LectureVo> allLecture();
}

package com.ruoyi.ai.mapper;

import com.ruoyi.ai.domain.ChatRecords;
import com.ruoyi.ai.domain.InterviewRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ruoyi
 * @since 2025-08-20
 */
public interface InterviewRecordsMapper extends BaseMapper<InterviewRecords> {

    List<InterviewRecords> getInterviewRecordsByPage(@Param("subject") String subject, @Param("openid") String openid,
                                                @Param("offset") int offset, @Param("limit") int limit);
}

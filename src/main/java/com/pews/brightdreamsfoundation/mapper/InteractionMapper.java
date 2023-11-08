package com.pews.brightdreamsfoundation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pews.brightdreamsfoundation.beans.Interaction;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface InteractionMapper extends BaseMapper<Interaction> {
    Long getExtentVideoTime(LocalDateTime start, LocalDateTime end, Long id);

    Long getExtentTextTime(LocalDateTime start, LocalDateTime end, Long id);
}

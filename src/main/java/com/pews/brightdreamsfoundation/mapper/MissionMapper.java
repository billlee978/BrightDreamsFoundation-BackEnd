package com.pews.brightdreamsfoundation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pews.brightdreamsfoundation.beans.Mission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MissionMapper extends BaseMapper<Mission> {
    int releaseMission(Long id);
}

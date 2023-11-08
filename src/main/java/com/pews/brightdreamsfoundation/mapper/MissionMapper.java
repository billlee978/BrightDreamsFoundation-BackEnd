package com.pews.brightdreamsfoundation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pews.brightdreamsfoundation.beans.Mission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MissionMapper extends BaseMapper<Mission> {
    int releaseMission(Long id);

    List<Mission> selectCompletedMission(Long id);
    List<Mission> selectUncompletedMission(Long id);

    List<Mission> searchCompletedMission(String keywords, Long id);

    List<Mission> searchUncompletedMission(String keywords, Long id);
}

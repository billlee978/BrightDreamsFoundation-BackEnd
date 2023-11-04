package com.pews.brightdreamsfoundation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pews.brightdreamsfoundation.beans.Mission;
import com.pews.brightdreamsfoundation.beans.MissionHistory;

import java.util.List;

public interface MissionService extends IService<Mission> {
    boolean releaseMission(Long id);

    List<Mission> selectUncompletedMission(Long id);

    List<Mission> getCompletedMission(Long id);
}

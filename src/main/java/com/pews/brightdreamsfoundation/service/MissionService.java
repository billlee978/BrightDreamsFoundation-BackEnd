package com.pews.brightdreamsfoundation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pews.brightdreamsfoundation.beans.Mission;
import com.pews.brightdreamsfoundation.beans.User;

import java.util.List;

public interface MissionService extends IService<Mission> {
    boolean releaseMission(Long id);

    List<Mission> selectUncompletedMission(Long id);

    List<Mission> selectCompletedMission(Long id);

    boolean reward(Long userId, Mission mission);

    List<Mission> searchCompleted(String keywords, Long id);

    List<Mission> searchUncompleted(String keywords, Long id);

    List<Mission> checkMissions(Long id);

    boolean addMissionHistory(Mission mission, Long id);
}

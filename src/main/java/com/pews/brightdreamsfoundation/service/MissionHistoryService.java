package com.pews.brightdreamsfoundation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pews.brightdreamsfoundation.beans.MissionHistory;

import java.util.List;

public interface MissionHistoryService extends IService<MissionHistory> {

    boolean judgeStudyMission(Long id, Byte status);

    boolean submit(MissionHistory history);

    List<MissionHistory> searchHistory(Long id, String keywords);
}

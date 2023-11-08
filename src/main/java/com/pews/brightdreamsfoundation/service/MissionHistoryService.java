package com.pews.brightdreamsfoundation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pews.brightdreamsfoundation.beans.MissionHistory;

public interface MissionHistoryService extends IService<MissionHistory> {

    boolean judgeStudyMission(Long id, Byte status);

    boolean submit(MissionHistory history);
}

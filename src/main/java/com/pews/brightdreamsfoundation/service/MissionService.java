package com.pews.brightdreamsfoundation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pews.brightdreamsfoundation.beans.Mission;

public interface MissionService extends IService<Mission> {
    boolean releaseMission(Long id);
}

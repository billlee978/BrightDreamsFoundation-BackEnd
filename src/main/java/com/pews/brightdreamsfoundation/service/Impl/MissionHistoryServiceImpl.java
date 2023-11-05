package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.Mission;
import com.pews.brightdreamsfoundation.beans.MissionHistory;
import com.pews.brightdreamsfoundation.mapper.MissionHistoryMapper;
import com.pews.brightdreamsfoundation.service.MissionHistoryService;
import com.pews.brightdreamsfoundation.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionHistoryServiceImpl extends ServiceImpl<MissionHistoryMapper, MissionHistory> implements MissionHistoryService {

    @Autowired
    MissionHistoryMapper mapper;

    @Autowired
    MissionService missionService;

    @Override
    public List<MissionHistory> list(Wrapper<MissionHistory> queryWrapper) {
        List<MissionHistory> histories = super.list(queryWrapper);
        for (MissionHistory history : histories) {
            LambdaQueryWrapper<Mission> missionWrapper = new LambdaQueryWrapper<>();
            missionWrapper.eq(Mission::getId, history.getMissionId());
            history.setMission(missionService.getOne(missionWrapper));
        }

        return histories;
    }
}

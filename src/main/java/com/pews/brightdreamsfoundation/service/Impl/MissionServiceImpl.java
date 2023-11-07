package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.Mission;
import com.pews.brightdreamsfoundation.beans.PointHistory;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.mapper.MissionHistoryMapper;
import com.pews.brightdreamsfoundation.mapper.MissionMapper;
import com.pews.brightdreamsfoundation.service.MissionService;
import com.pews.brightdreamsfoundation.service.PointHistoryService;
import com.pews.brightdreamsfoundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class MissionServiceImpl extends ServiceImpl<MissionMapper, Mission> implements MissionService {
    @Autowired
    MissionMapper missionMapper;
    @Autowired
    MissionHistoryMapper missionHistoryMapper;
    @Autowired
    PointHistoryService pointHistoryService;
    @Autowired
    UserService userService;

    @Override
    public boolean releaseMission(Long id) {
        return missionMapper.releaseMission(id) == 1;
    }

    @Override
    public List<Mission> selectCompletedMission(Long id) {
        return missionMapper.selectCompletedMission(id);
    }

    @Override
    public boolean reward(Long userId, Mission mission) {
        User user = userService.getById(userId);
        user.setPoints(user.getPoints() + mission.getReward());
        pointHistoryService.addPointHistory(user, mission);
        return userService.updateById(user);

    }


    @Override
    public List<Mission> selectUncompletedMission(Long id) {
        Date date = new Date();
        QueryWrapper<Mission> wrapper = new QueryWrapper<>();
        wrapper.eq("IS_RELEASED", 1);
        wrapper.gt("DEADLINE", date.getTime());

        List<Mission> missions = missionMapper.selectList(wrapper);
        List<Mission> completedMissions = selectCompletedMission(id);
        List<Mission> uncompletedMissions = new ArrayList<>();

        for(Mission mission : missions) {
            boolean flag = true;
            for (Mission completedOne : completedMissions) {
                if (Objects.equals(mission.getId(), completedOne.getId())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                uncompletedMissions.add(mission);
            }
        }

        return uncompletedMissions;
    }

}

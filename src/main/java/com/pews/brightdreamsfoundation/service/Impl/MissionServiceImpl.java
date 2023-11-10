package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.Mission;
import com.pews.brightdreamsfoundation.beans.MissionHistory;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.mapper.InteractionMapper;
import com.pews.brightdreamsfoundation.mapper.MissionHistoryMapper;
import com.pews.brightdreamsfoundation.mapper.MissionMapper;
import com.pews.brightdreamsfoundation.service.MissionService;
import com.pews.brightdreamsfoundation.service.PointHistoryService;
import com.pews.brightdreamsfoundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    InteractionMapper interactionMapper;

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
    public List<Mission> searchCompleted(String keywords, Long id) {
        keywords = "%" + keywords + "%";
        return missionMapper.searchCompletedMission(keywords, id);
    }

    @Override
    public List<Mission> searchUncompleted(String keywords, Long id) {
        keywords = "%" + keywords + "%";
        return missionMapper.searchUncompletedMission(keywords, id);
    }

    @Override
    public List<Mission> checkMissions(Long id) {
        List<Mission> missionReached = new ArrayList<>();
        List<Mission> interactiveMissions = missionMapper.selectUncompletedInteractiveMission(id);
        System.out.println("shabi");
        for (Mission mission : interactiveMissions) {
            int target = mission.getTargetNum();
            Long process = 0L;
            if (mission.getKind() == 2) {
                // 聊天任务
                process = interactionMapper.getExtentTextTime(mission.getReleaseDate(), mission.getDeadline(), id);
            } else {
                // 视频通话任务
                process = interactionMapper.getExtentVideoTime(mission.getReleaseDate(), mission.getDeadline(), id);
            }
            if (process >= mission.getTargetNum()) {
                reward(id, mission);
                addCompletedMissionHistory(mission, id);
                missionReached.add(mission);
            }
        }
        return missionReached;
    }

    @Override
    public boolean addCompletedMissionHistory(Mission mission, Long id) {
        MissionHistory history = new MissionHistory();
        history.setStatus((byte) 1);
        history.setMission(mission);
        history.setMissionId(mission.getId());
        history.setFinishDate(LocalDateTime.now());
        history.setDescription(null);
        history.setUserId(id);

        return missionHistoryMapper.insert(history) == 1;
    }


    @Override
    public List<Mission> selectUncompletedMission(Long id) {
        List<Mission> missions = missionMapper.selectUncompletedMission(id);
        return missions;
    }



}

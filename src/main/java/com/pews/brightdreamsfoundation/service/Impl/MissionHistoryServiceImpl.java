package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.Mission;
import com.pews.brightdreamsfoundation.beans.MissionHistory;
import com.pews.brightdreamsfoundation.beans.PointHistory;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.mapper.MissionHistoryMapper;
import com.pews.brightdreamsfoundation.service.MissionHistoryService;
import com.pews.brightdreamsfoundation.service.MissionService;
import com.pews.brightdreamsfoundation.service.PointHistoryService;
import com.pews.brightdreamsfoundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MissionHistoryServiceImpl extends ServiceImpl<MissionHistoryMapper, MissionHistory> implements MissionHistoryService {

    @Autowired
    MissionHistoryMapper mapper;

    @Autowired
    MissionService missionService;

    @Autowired
    UserService userService;

    @Autowired
    PointHistoryService pointHistoryService;

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

    @Override
    public boolean judgeStudyMission(Long id, Byte result) {
        LambdaUpdateWrapper<MissionHistory> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MissionHistory::getId, id);
        MissionHistory history = list(wrapper).get(0);
        history.setStatus(result);
        boolean flag = updateById(history);
        if (flag) {
            // 发放奖品
            if (result == 1) {
                return missionService.reward(history.getUserId(), history.getMission());
            }

            return true;

        } else {
            return false;
        }
    }

    @Override
    public boolean submit(MissionHistory history) {
        LambdaQueryWrapper<MissionHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MissionHistory::getMissionId, history.getMissionId())
                .eq(MissionHistory::getUserId, history.getUserId())
                .ne(MissionHistory::getStatus, 2);
        List<MissionHistory> repeat = list(wrapper);
        if (repeat.size() > 0) {
            return false;
        }else {
            history.setFinishDate(LocalDateTime.now());
            history.setStatus((byte) 0);
            save(history);
            return true;
        }
    }
}

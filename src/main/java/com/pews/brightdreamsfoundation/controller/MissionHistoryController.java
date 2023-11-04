package com.pews.brightdreamsfoundation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import com.pews.brightdreamsfoundation.beans.MissionHistory;
import com.pews.brightdreamsfoundation.service.MissionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("missionHistory")
public class MissionHistoryController {

    @Autowired
    MissionHistoryService missionHistoryService;

    @PostMapping("submit")
    public HttpResponseEntity submit(@RequestBody MissionHistory history) {
        LambdaQueryWrapper<MissionHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MissionHistory::getMissionId, history.getMissionId())
                .eq(MissionHistory::getUserId, history.getUserId());
        List<MissionHistory> repeat = missionHistoryService.list(wrapper);
        if (repeat.size() > 0) {
            return new HttpResponseEntity(404, null, "你已经提交过该任务了！");
        } else {
            history.setFinishDate(LocalDateTime.now());
            boolean flag = missionHistoryService.save(history);
            if (flag) {
                return new HttpResponseEntity(200, null, "成功提交!");
            } else {
                return new HttpResponseEntity(404, null, "提交失败!");
            }
        }
    }


}

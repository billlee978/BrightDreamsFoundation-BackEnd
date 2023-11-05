package com.pews.brightdreamsfoundation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import com.pews.brightdreamsfoundation.beans.MissionHistory;
import com.pews.brightdreamsfoundation.service.MissionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取该用户的所有任务历史记录
     * @param id
     * @return
     */
    @GetMapping("get/{id}")
    public HttpResponseEntity getMissionHistoryByUserId(@PathVariable("id") Long id) {
        LambdaQueryWrapper<MissionHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MissionHistory::getUserId, id);
        List<MissionHistory> histories = missionHistoryService.list(wrapper);

        return new HttpResponseEntity(200, histories, "查询成功!");
    }

    /**
     * 获取某条任务历史记录
     * @param id
     * @return
     */
    @GetMapping("select/{id}")
    public HttpResponseEntity getMissionHistoryByMissionId(@PathVariable("id") Long id) {
        LambdaQueryWrapper<MissionHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MissionHistory::getId, id);
        List<MissionHistory> histories = missionHistoryService.list(wrapper);

        if (histories.size() == 0) {
            return new HttpResponseEntity(404, null,"查询失败，该记录不存在!");
        }

        return new HttpResponseEntity(200, histories.get(0), "查询成功!");
    }


}

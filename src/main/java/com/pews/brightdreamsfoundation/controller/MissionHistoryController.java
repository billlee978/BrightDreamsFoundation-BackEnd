package com.pews.brightdreamsfoundation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import com.pews.brightdreamsfoundation.beans.MissionHistory;
import com.pews.brightdreamsfoundation.service.MissionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("missionHistory")
public class MissionHistoryController {

    @Autowired
    MissionHistoryService missionHistoryService;

    @PostMapping("submit")
    public HttpResponseEntity submit(@RequestBody MissionHistory history) {

        boolean flag = missionHistoryService.submit(history);
        if (flag) {
            return new HttpResponseEntity(200, null, "成功提交!");
        } else {
            return new HttpResponseEntity(404, null, "提交失败,你已经提交过这个任务了!");
        }

    }

    /**
     * 获取该用户的所有任务历史记录
     * @param id 用户id
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
     * 获取某人某条任务的所有历史记录
     * @param
     * @return
     */
    @GetMapping("select/{userId}/{missionId}")
    public HttpResponseEntity getMissionHistoryByMissionId(@PathVariable("userId") Long userId, @PathVariable("missionId") Long missionId) {
        LambdaQueryWrapper<MissionHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MissionHistory::getMissionId, missionId);
        wrapper.eq(MissionHistory::getUserId, userId);
        List<MissionHistory> histories = missionHistoryService.list(wrapper);

        if (histories.size() == 0) {
            return new HttpResponseEntity(404, null,"查询失败，该记录不存在!");
        }

        return new HttpResponseEntity(200, histories.get(0), "查询成功!");
    }

    /**
     * 获取某人某条任务的最新记录
     * @param
     * @return
     */
    @GetMapping("selectNewest/{userId}/{missionId}")
    public HttpResponseEntity getNewestMissionHistoryByMissionId(@PathVariable("userId") Long userId, @PathVariable("missionId") Long missionId) {
        QueryWrapper<MissionHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("MISSION_ID", missionId);
        wrapper.eq("USER_ID", userId);
        wrapper.orderBy(true, false, "FINISH_DATE");
        List<MissionHistory> histories = missionHistoryService.list(wrapper);

        if (histories.size() == 0) {
            return new HttpResponseEntity(404, null,"查询失败，该记录不存在!");
        }

        return new HttpResponseEntity(200, histories.get(0), "查询成功!");
    }

    /**
     * 驱动桩，单纯是给志愿者审批请求用的
     * @param id
     * @return
     */
    @PutMapping("{id}/{result}")
    public HttpResponseEntity judgeSubmission(@PathVariable("id") Long id, @PathVariable("result") Byte result) {
        boolean flag = missionHistoryService.judgeStudyMission(id, result);
        if (flag) {
            return new HttpResponseEntity(200, null, "修改成功!");
        } else {
            return new HttpResponseEntity(404, null, "修改失败!");
        }


    }

    /**
     * 获取最新的完成任务历史记录
     * @param id 用户id
     * @return
     */
    @GetMapping("selectNewest/{id}")
    public HttpResponseEntity selectNewestCompletedHistories(@PathVariable("id") Long id) {
        QueryWrapper<MissionHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("USER_ID", id);
        wrapper.and(i->i.eq("status",1).or().eq("status", 0));

        List<MissionHistory> list = missionHistoryService.list(wrapper);
        return new HttpResponseEntity(200, list, "查询成功!");
    }


}

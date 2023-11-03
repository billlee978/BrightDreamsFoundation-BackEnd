package com.pews.brightdreamsfoundation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pews.brightdreamsfoundation.beans.Mission;
import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mission")
public class MissionController {
    @Autowired
    MissionService missionService;

    @GetMapping("{page}/{limit}")
    public HttpResponseEntity getMissionPage(@PathVariable("page") Long page,
                                              @PathVariable("limit") Long limit,
                                              Mission Mission) {
        IPage<Mission> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<Mission> wrapper = new LambdaQueryWrapper<>();
        IPage<Mission> iPage = missionService.page(pageParam, wrapper);
        for (Mission mission : iPage.getRecords()) {
            switch (mission.getKind()) {
                case 0:
                    mission.setKindName("互动任务");
                case 1:
                    mission.setKindName("学习任务");
            }
        }

        return new HttpResponseEntity(200, iPage, "OK!");
    }

    @PostMapping("save")
    public HttpResponseEntity saveMission(@RequestBody Mission mission) {
        mission.setId(0L);
        return missionService.save(mission) ? HttpResponseEntity.ok() : new HttpResponseEntity(500, null, "Failed!");
    }

    @DeleteMapping("batchRemove")
    public HttpResponseEntity batchRemove(@RequestBody List<Long> idList) {
        return missionService.removeBatchByIds(idList) ? HttpResponseEntity.ok() : new HttpResponseEntity(500, null, "Failed!");
    }

    @PostMapping("releaseMission/{id}")
    public HttpResponseEntity releaseMission(@PathVariable("id") Long id) {
        return missionService.releaseMission(id) ? HttpResponseEntity.ok() : new HttpResponseEntity(500, null, "Failed!");
    }

    @DeleteMapping("remove/{id}")
    public HttpResponseEntity removeById(@PathVariable("id") Long id) {
        LambdaQueryWrapper<Mission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Mission::getId, id);
        return missionService.remove(wrapper) ? HttpResponseEntity.ok() : new HttpResponseEntity(500, null, "Failed!");
    }

    /**
     * 获得用户未完成任务列表
     * @param id
     * @return
     */
    @GetMapping("get/{id}")
    public HttpResponseEntity getMissionUncompleted(@PathVariable("id") Long id) {
        List<Mission> missions = missionService.selectUncompletedMission(id);

        return new HttpResponseEntity(200, missions, "查询成功!");

    }

    /**
     * 获得用户完成任务历史记录
     * @param id
     * @return
     */
    @GetMapping("history/{id}")
    public HttpResponseEntity getMissionCompleted(@PathVariable("id") Long id) {
        List<Mission> missions = missionService.getCompletedMission(id);

        return new HttpResponseEntity(200, missions, "查询成功!");

    }
}

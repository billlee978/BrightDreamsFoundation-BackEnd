package com.pews.brightdreamsfoundation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pews.brightdreamsfoundation.beans.Mission;
import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import com.pews.brightdreamsfoundation.beans.MissionHistory;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("mission")
public class MissionController {
    @Autowired
    MissionService missionService;

    @Value("${upload.path}")
    String filePathName;

    @Value("${server.port}")
    String port;

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
                    mission.setKindName("上传文件任务");
                    break;
                case 1:
                    mission.setKindName("上传视频任务");
                    break;
                case 2:
                    mission.setKindName("聊天任务");
                    break;
                case 3:
                    mission.setKindName("视频通话任务");
                    break;
            }
        }

        return new HttpResponseEntity(200, iPage, "OK!");
    }

    @PostMapping("save")
    public HttpResponseEntity saveMission(@RequestBody Mission mission) {
        mission.setId(0L);
        return missionService.save(mission) ? HttpResponseEntity.ok() : new HttpResponseEntity(500, null, "Failed!");
    }

    @RequestMapping("upload")
    public HttpResponseEntity uploadFile(MultipartFile file) {
        try {
            String[] fileOriginalName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
            String filename = UUID.randomUUID() + "." + fileOriginalName[fileOriginalName.length - 1];
            Path filepath = Path.of(filePathName, filename);
            file.transferTo(filepath);
            return new HttpResponseEntity(200, "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/" + filename, "OK");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

}

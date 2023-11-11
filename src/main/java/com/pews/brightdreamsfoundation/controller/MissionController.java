package com.pews.brightdreamsfoundation.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pews.brightdreamsfoundation.beans.Mission;
import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import com.pews.brightdreamsfoundation.beans.MissionHistory;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.service.MissionService;
import org.apache.catalina.util.LifecycleMBeanBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 任务系统controller
 */
@RestController
@RequestMapping("mission")
public class MissionController {
    @Autowired
    MissionService missionService;
    @Autowired
    OSS ossClient;
    @Value("${aliyun.bucketName}")
    String bucketName;
    @Value("${aliyun.endpoint}")
    String endpoint;
    @Value("${aliyun.area}")
    String area;

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
            String filename = "img/" + UUID.randomUUID() + "." + fileOriginalName[fileOriginalName.length - 1];
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, file.getInputStream());
            ossClient.putObject(putObjectRequest);
            String pictureURL = "https://" + bucketName + "." + area + "/" + filename;

            return new HttpResponseEntity(200, pictureURL, "OK");
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

    /**
     * 获得用户已完成任务列表
     * @param id 用户id
     * @return
     */
    @GetMapping("getCompleted/{id}")
    public HttpResponseEntity getMissionCompleted(@PathVariable("id") Long id) {
        List<Mission> missions = missionService.selectCompletedMission(id);

        return new HttpResponseEntity(200, missions, "查询成功!");

    }

    /**
     * 根据任务id获取任务信息
     * @param id
     * @return
     */
    @GetMapping("getById/{id}")
    public HttpResponseEntity getMissionById(@PathVariable("id") Long id) {
        Mission mission = missionService.getById(id);
        if (mission == null) {
            return new HttpResponseEntity(404, null, "查询失败!");
        } else {
            return new HttpResponseEntity(200, mission, "查询成功!");
        }

    }


    /**
     * 根据关键词搜索未完成任务
     * @param keywords
     * @param id 用户id
     * @return
     */
    @PostMapping("searchUncompleted/{id}")
    public HttpResponseEntity searchUncompleted(String keywords, @PathVariable("id") Long id) {
        List<Mission> missions = missionService.searchUncompleted(keywords, id);
        return new HttpResponseEntity(200, missions, "查询成功!");


    }

    /**
     * 根据关键词搜索已完成任务
     * @param keywords
     * @param id
     * @return
     */
    @PostMapping("searchCompleted/{id}")
    public HttpResponseEntity searchCompleted(String keywords, @PathVariable("id") Long id) {
        List<Mission> missions = missionService.searchCompleted(keywords, id);

        return new HttpResponseEntity(200, missions, "查询成功!");

    }


    @GetMapping("check/{id}")
    public HttpResponseEntity checkMissionReached(@PathVariable("id") Long id) {
        List<Mission> missionReached = missionService.checkMissions(id);

        if (missionReached.size() > 0) {
            return new HttpResponseEntity(200, missionReached, "完成任务!");
        } else {
            return new HttpResponseEntity(200, missionReached, "没有新完成任务!");
        }

    }





}

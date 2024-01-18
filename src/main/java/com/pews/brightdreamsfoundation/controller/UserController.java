package com.pews.brightdreamsfoundation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 用户controller
 */
@RestController
@RequestMapping("userInfo")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    /**
     * 用户列表分页查询
     * @param page
     * @param limit
     * @param user
     * @return
     */
    @GetMapping("{page}/{limit}")
    public HttpResponseEntity getUserPage(@PathVariable("page") Long page,
                                          @PathVariable("limit") Long limit,
                                          User user) {
        IPage<User> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (user.getUsername() != null) {
            wrapper.like(User::getUsername, user.getUsername());
        }
        if (user.getRealName() != null) {
            wrapper.like(User::getRealName, user.getRealName());
        }
        if (user.getRole() != 0) {
            wrapper.eq(User::getRole, user.getRole());
        }

        IPage<User> iPage = userService.page(pageParam, wrapper);
        for (User record : iPage.getRecords()) {
            switch (record.getRole()) {
                case 0:
                    record.setRoleName("管理员");
                    break;
                case 1:
                    record.setRoleName("儿童");
                    break;
                case 2:
                    record.setRoleName("志愿者");
                    break;
                case 3:
                    record.setRoleName("捐赠者");
                    break;
                default:
                    break;
            }

            switch (record.getGender()) {
                case 0:
                    record.setGenderStr("女");
                    break;
                case 1:
                    record.setGenderStr("男");
                    break;
            }
        }
        return new HttpResponseEntity(200, iPage, "OK");
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("remove/{id}")
    public HttpResponseEntity removeUser(@PathVariable("id") Long id) {
        return userService.removeById(id) ? HttpResponseEntity.ok() : new HttpResponseEntity(500, null, "Failed!");
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("save")
    public HttpResponseEntity insertUser(@RequestBody User user) {
        user.setId(0L);
        return userService.save(user) ? HttpResponseEntity.ok() : new HttpResponseEntity(500, null, "Failed!");
    }

    /**
     * 查询用户
     * @param id
     * @return
     */
    @GetMapping("get/{id}")
    public HttpResponseEntity getOneUser(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        if (!ObjectUtils.isEmpty(user)) {
            return new HttpResponseEntity(200, user, "OK!");
        } else {
            return new HttpResponseEntity(404, null, "Not Found!");
        }
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PutMapping("/update")
    public HttpResponseEntity updateUser(@RequestBody User user) {
        if (!ObjectUtils.isEmpty(user)) {
            return userService.updateById(user) ? HttpResponseEntity.ok() : new HttpResponseEntity(500, null, "Failed!");
        } else {
            return new HttpResponseEntity(404, null, "User can't be null!");
        }
    }

    /**
     * 批量移除用户
     * @param idList
     * @return
     */
    @DeleteMapping("batchRemove")
    public HttpResponseEntity batchRemove(@RequestBody List<String> idList) {
        return userService.removeBatchByIds(idList) ? HttpResponseEntity.ok() : new HttpResponseEntity(500, null, "Failed!");
    }

    /**
     * 获取绑定信息
     * @param id
     * @param isBind
     * @param isFromChild
     * @return
     */
    @GetMapping("getBind/{id}/{isBind}/{isFromChild}")
    public HttpResponseEntity getBind(@PathVariable("id") Long id,
                                      @PathVariable("isBind") Boolean isBind,
                                      @PathVariable("isFromChild") Boolean isFromChild) {
        if (isBind) {
            return new HttpResponseEntity(200, userService.getBind(id, isFromChild), "OK!");
        } else {
            return new HttpResponseEntity(200, userService.getNotBind(id, isFromChild), "OK!");
        }
    }

    /**
     * 获取绑定对象id
     * @param id
     * @param idList
     * @param isChildBind 是否来自children请求
     * @return
     */
    @PostMapping("bind/{id}/{isChildBind}")
    public HttpResponseEntity bind(@PathVariable("id") Long id, @RequestBody List<Long> idList, @PathVariable("isChildBind") Boolean isChildBind) {
        return new HttpResponseEntity(200, userService.bind(id, idList, isChildBind), "OK!");
    }

    /**
     * 接触绑定
     * @param id
     * @param idList
     * @param isChildUnbind
     * @return
     */
    @DeleteMapping("unbind/{id}/{isChildUnbind}")
    public HttpResponseEntity unbind(@PathVariable("id") Long id, @RequestBody List<Long> idList, @PathVariable("isChildUnbind") Boolean isChildUnbind) {
        return new HttpResponseEntity(200, userService.unbind(id, idList, isChildUnbind), "OK!");
    }

    /**
     * 注册功能
     * @param user
     * @return
     */
    @PostMapping("register")
    public HttpResponseEntity register(@RequestBody User user) {
        user.setRole((byte) 1);
        if (userService.register(user) != 0) {
            return new HttpResponseEntity(404, null, "注册失败，用户已经存在!");
        } else {
            return new HttpResponseEntity(200, null, "注册成功!");
        }
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PostMapping("update")
    public HttpResponseEntity update(@RequestBody User user) {

        boolean flag = userService.updateInfo(user);
        if (flag) {
            return new HttpResponseEntity(200, user, "修改成功!");
        } else {
            return new HttpResponseEntity(404, null, "修改失败!");
        }
    }


}

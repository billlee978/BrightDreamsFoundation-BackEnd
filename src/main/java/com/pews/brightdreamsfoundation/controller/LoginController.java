package com.pews.brightdreamsfoundation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("user")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public HttpResponseEntity login(@RequestBody User user) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("USERNAME", user.getUsername().trim());
        wrapper.eq("PASSWORD", user.getPassword().trim());
        List<User> users = userService.list(wrapper);
        if (users.isEmpty()) {
            return new HttpResponseEntity(404, null, "No such user!");
        } else {
            User currentUser = users.get(0);
            if (currentUser.getRole() == 0) {
                map.put("token", "admin");
                return new HttpResponseEntity(200, map, "OK");
            }else {
                return new HttpResponseEntity(200, user, "OK");
            }
        }


    }

    @GetMapping("info")
    public HttpResponseEntity getInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("roles", "[admin]");
        map.put("name", "admin");
        map.put("avatar", "https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        return new HttpResponseEntity(200, map, "OK");
    }

    @PostMapping("logout")
    public HttpResponseEntity logout() {
        return HttpResponseEntity.ok();
    }
}

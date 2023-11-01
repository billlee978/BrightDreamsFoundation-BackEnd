package com.pews.brightdreamsfoundation.controller;

import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("admin")
public class LoginController {

    @PostMapping("login")
    public HttpResponseEntity login() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", "admin");
        return new HttpResponseEntity(200, map, "OK");
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

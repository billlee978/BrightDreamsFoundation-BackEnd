package com.pews.brightdreamsfoundation.controller;

import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("interact/")
public class InteractionController {
    @GetMapping("")
    String getChat() {
        return "index";
    }

}

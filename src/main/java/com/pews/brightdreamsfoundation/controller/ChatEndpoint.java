package com.pews.brightdreamsfoundation.controller;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pews.brightdreamsfoundation.beans.Interaction;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.service.InteractionService;
import com.pews.brightdreamsfoundation.service.UserService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@ServerEndpoint("/chat/{id}")
public class ChatEndpoint {
    private static ConcurrentHashMap<Long, ChatEndpoint> webSocketSet = new ConcurrentHashMap<>();
    private static InteractionService interactionService;
    private User currentUser;
    private Long chatUserId;
    private Session session;

    @Autowired
    public void setInteractionService(InteractionService service) {
        ChatEndpoint.interactionService = service;
    }

    private static UserService userService;
    @Autowired
    public void setUserService(UserService service) {
        ChatEndpoint.userService = service;
    }

    @OnOpen
    public void openSession(@PathParam("id") Long id, Session session){
        System.out.println("建立连接");
        // 建立连接后先将session入池
        if (webSocketSet.containsKey(id)) {
            return;
        }
        User user = userService.getById(id);
        System.out.println(user);
        if (user != null) {
            webSocketSet.put(id, this);
            this.currentUser = user;
            this.session = session;
            this.chatUserId = userService.getBind(id, user.getRole() == 1).get(0).getId();
        }
        try {
            // 将之前的消息记录发送回去
            List<Interaction> list = interactionService.selectAllHistoryInteraction(user);
            session.getAsyncRemote().sendText(JSON.toJSONString(list));
        } catch (Exception exception) {
            System.out.println("建立连接时出现问题!");
        }


    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(message);
        Interaction interaction = JSON.parseObject(message, Interaction.class);
        interactionService.save(interaction);
        if (ChatEndpoint.webSocketSet.containsKey(this.chatUserId)) {
            ChatEndpoint.webSocketSet.get(this.chatUserId).session.getAsyncRemote().sendText(JSON.toJSONString(interaction));
        }

    }


    @OnClose
    public void onClose(@PathParam("id") Long id, Session session) {
        //当前的Session 移除
        System.out.println("关闭本聊天窗口!");
        ChatEndpoint.webSocketSet.remove(id);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            System.out.println("会话出现异常");;
        }
    }




}

package com.pews.brightdreamsfoundation.controller;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pews.brightdreamsfoundation.beans.Interaction;
import com.pews.brightdreamsfoundation.beans.MissionHistory;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.service.InteractionService;
import com.pews.brightdreamsfoundation.service.MissionService;
import com.pews.brightdreamsfoundation.service.UserService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket终端类
 */
@RestController
@ServerEndpoint("/chat/{id}")
public class ChatEndpoint {
    private static ConcurrentHashMap<Long, ChatEndpoint> webSocketSet = new ConcurrentHashMap<>();
    private static InteractionService interactionService;

    private static MissionService missionService;

    // 当前用户
    private User currentUser;
    // 当前对话用户id
    private Long chatUserId;
    // 当前websocket建立的会话
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


    @Autowired
    public void setMissionService(MissionService service) {
        ChatEndpoint.missionService = service;
    }


    /**
     * 建立会话，并且把当前会话存入map，同时将用户历史记录返回
     * @param id
     * @param session
     */
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

    /**
     * 将该消息记录存入数据库，同时检查交谈用户会话是否存在，如果存在则将该信息发送
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(message);
        Interaction interaction = JSON.parseObject(message, Interaction.class);
        interaction.setReceiverId(this.chatUserId);
        interactionService.save(interaction);
        try {
            missionService.checkMissions(currentUser.getId());
            if (ChatEndpoint.webSocketSet.containsKey(this.chatUserId)) {
                ChatEndpoint.webSocketSet.get(this.chatUserId).session.getAsyncRemote().sendText(JSON.toJSONString(interaction));
            }
        }catch (Exception e) {
            System.out.println(e);
        }


    }


    /**
     * 关闭窗口时调用的函数
     * @param id
     * @param session
     */
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

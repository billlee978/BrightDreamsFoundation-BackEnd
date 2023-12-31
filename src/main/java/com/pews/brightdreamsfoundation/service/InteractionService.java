package com.pews.brightdreamsfoundation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pews.brightdreamsfoundation.beans.Interaction;
import com.pews.brightdreamsfoundation.beans.User;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.List;

public interface InteractionService extends IService<Interaction> {

    List<Interaction> selectAllHistoryInteraction(User user);
}

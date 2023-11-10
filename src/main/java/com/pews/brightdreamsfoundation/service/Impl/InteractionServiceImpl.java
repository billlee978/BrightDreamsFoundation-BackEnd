package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.Interaction;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.mapper.InteractionMapper;
import com.pews.brightdreamsfoundation.mapper.UserMapper;
import com.pews.brightdreamsfoundation.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteractionServiceImpl extends ServiceImpl<InteractionMapper, Interaction> implements InteractionService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    InteractionMapper interactionMapper;
    @Override
    public List<Interaction> selectAllHistoryInteraction(User user) {
        LambdaQueryWrapper<Interaction> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(true, Interaction::getInteractTime);
        if (user.getRole() == 1) {
            wrapper.eq(Interaction::getChildrenId, user.getId());
        } else {
            wrapper.eq(Interaction::getVolunteerId, user.getId());
        }

        return interactionMapper.selectList(wrapper);
    }
}

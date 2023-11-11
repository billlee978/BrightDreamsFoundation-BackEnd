package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.Interaction;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.mapper.InteractionMapper;
import com.pews.brightdreamsfoundation.mapper.UserMapper;
import com.pews.brightdreamsfoundation.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 互动记录业务层
 */
@Service
public class InteractionServiceImpl extends ServiceImpl<InteractionMapper, Interaction> implements InteractionService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    InteractionMapper interactionMapper;

    /**
     * 根据用户信息获取他的所有互动记录
     * @param user
     * @return
     */
    @Override
    public List<Interaction> selectAllHistoryInteraction(User user) {
        QueryWrapper<Interaction> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc(true, "INTERACT_TIME");
        wrapper.and(i->i.eq("SENDER_ID", user.getId()).or().eq("RECEIVER_ID", user.getId()));

        return interactionMapper.selectList(wrapper);
    }
}

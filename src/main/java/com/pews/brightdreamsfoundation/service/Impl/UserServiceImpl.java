package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.mapper.UserMapper;
import com.pews.brightdreamsfoundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> getBind(Long id, Boolean isFromChild) {
        if (isFromChild) {
            return userMapper.getVulFromBind(id);
        } else {
            return userMapper.getChildFromBind(id);
        }
    }

    @Override
    public List<User> getNotBind(Long id, Boolean isFromChild) {
        if (isFromChild) {
            List<User> vulBind = userMapper.getVulFromBind(id);
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getRole, (byte) 2);
            List<User> allVul = userMapper.selectList(wrapper);
            return allVul.stream().filter(user -> !vulBind.contains(user)).toList();
        } else {
            List<User> childBind = userMapper.getChildFromBind(id);
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getRole, (byte) 1);
            List<User> allChild = userMapper.selectList(wrapper);
            return allChild.stream().filter(user -> !childBind.contains(user)).toList();
        }

    }

    @Override
    public int bind(Long id, List<Long> idList, Boolean isChildBind) {
        int count = 0;
        if (isChildBind) {
            for (Long l : idList) {
                count += userMapper.bind(l,id);
            }
        } else {
            for (Long l : idList) {
                count += userMapper.bind(id, l);
            }
        }
        return count;
    }

    @Override
    public int unbind(Long id, List<Long> idList, Boolean isChildUnbind) {
        int count = 0;
        if (isChildUnbind) {
            for (Long l : idList) {
                count += userMapper.unbind(l,id);
            }
        } else {
            for (Long l : idList) {
                count += userMapper.unbind(id, l);
            }
        }
        return count;
    }

    @Override
    public int register(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("USERNAME",user.getUsername());
        List<User> users = userMapper.selectList(wrapper);
        if (users.size() >= 1) {
            return 1;
        }else {
            userMapper.insert(user);
            return 0;
        }
    }
}

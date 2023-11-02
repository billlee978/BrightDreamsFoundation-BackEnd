package com.pews.brightdreamsfoundation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pews.brightdreamsfoundation.beans.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> getBind(Long id, Boolean isFromChild);

    List<User> getNotBind(Long id, Boolean isFromChild);

    int bind(Long id, List<Long> idList, Boolean isChildBind);

    int unbind(Long id, List<Long> idList, Boolean isChildUnbind);

    int register(User user);
}

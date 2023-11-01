package com.pews.brightdreamsfoundation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pews.brightdreamsfoundation.beans.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> getVulFromBind(Long id);

    List<User> getChildFromBind(Long id);

    int bind(Long vulId, Long childId);

    int unbind(Long vulId, Long childId);
}

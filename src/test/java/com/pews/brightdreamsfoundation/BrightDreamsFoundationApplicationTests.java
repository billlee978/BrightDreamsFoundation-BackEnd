package com.pews.brightdreamsfoundation;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.mapper.UserMapper;
import com.pews.brightdreamsfoundation.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BrightDreamsFoundationApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    public void testMapperSelect() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, "pews");
        User user = userMapper.selectOne(wrapper);
        wrapper.clear();
        System.out.println(user);
        List<User> users = userMapper.selectList(null);
        for (User user1 : users) {
            System.out.println(user1);
        }
    }

    @Test
    public void testMapperInsert() {
        User user = new User();
        user.setId(0L);
        user.setUsername("lv");
        user.setPassword("678");
        user.setRole((byte) 2);
        System.out.println(userMapper.insert(user));
    }

    @Test
    public void testMapperUpdate() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId,4L);
        User user = new User();
        user.setUsername("lvmuze");
        user.setPassword("456");
        System.out.println(userMapper.update(user,wrapper));
    }

    @Test
    public void testMapperDelete() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId,4L);
        System.out.println(userMapper.delete(wrapper));
    }

    @Test
    public void testMapperPage() {
        //调用service的方法实现
        //1 创建Page对象，传递分页相关参数
        //page 当前页  limit 每页显示记录数
        int page = 0;
        int limit = 5;
        Page<User> pageParam = new Page<>(page,limit);

        //2 封装条件，判断条件是否为空，不为空进行封装
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        //3 调用方法实现
        IPage<User> pageModel = userMapper.selectPage(pageParam, wrapper);
        System.out.println(pageModel);
    }

    @Test
    public void testGetVulBind() {
        for (User user : userMapper.getVulFromBind(1L)) {
            System.out.println(user);
        }

        for (User user : userService.getVulNotBind(1L)) {
            System.out.println(user);
        }

    }

}

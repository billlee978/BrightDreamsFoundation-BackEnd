package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.*;
import com.pews.brightdreamsfoundation.mapper.GoodMapper;
import com.pews.brightdreamsfoundation.service.GoodService;
import com.pews.brightdreamsfoundation.service.OrderService;
import com.pews.brightdreamsfoundation.service.PointHistoryService;
import com.pews.brightdreamsfoundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品业务逻辑代码
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {
    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private PointHistoryService pointHistoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     * 购买商品功能：
     * 逻辑1：减少库存
     * 逻辑2：减少积分
     * 逻辑3：增加减少积分的记录
     * 逻辑4：创建购买订单
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void buyGoods(Good good, User user, Order order) {

        //减少库存并更新数据库
        good.setStock(good.getStock() - order.getAmount());
        goodMapper.updateById(good);

        //减少积分并更新数据库
        user.setPoints(user.getPoints() - order.getTotal());
        userService.updateById(user);

        //创建购买订单
        orderService.save(order);

        //增加减少积分的记录
        pointHistoryService.addPointHistory(user, order);
    }

    /**
     * 商品排序功能：
     */
    public List<Good> sortGoods(){
        return goodMapper.sortGoods();
    }

    @Override
    public boolean releaseGood(Long id) {
        return goodMapper.releaseGood(id) == 1;
    }
}

package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.Order;
import com.pews.brightdreamsfoundation.mapper.GoodMapper;
import com.pews.brightdreamsfoundation.mapper.OrderMapper;
import com.pews.brightdreamsfoundation.service.GoodService;
import com.pews.brightdreamsfoundation.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private GoodMapper goodMapper;
    @Override
    public List<Order> list(Wrapper<Order> queryWrapper) {
        List<Order> orders = super.list(queryWrapper);
        fillGoodToOrder(orders);

        return orders;
    }

    @Override
    public void fillGoodToOrder(List<Order> orders) {
        for (Order order : orders) {
            order.setGood(goodMapper.selectById(order.getGoodId()));
        }
    }
}

package com.pews.brightdreamsfoundation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import com.pews.brightdreamsfoundation.beans.Order;
import com.pews.brightdreamsfoundation.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 获取某user的所有订单
     * @param id 用户id
     * @return
     */
    @GetMapping("{id}")
    public HttpResponseEntity selectAllOrders(@PathVariable("id") int id) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, id);
        List<Order> orders = orderService.list(wrapper);

        return new HttpResponseEntity(200, orders, "查询成功!");
    }

    /**
     * 添加订单
     * @param order
     * @return
     */
    @PostMapping("add")
    public HttpResponseEntity addOrder(@RequestBody Order order) {
        order.setCreateDate(LocalDateTime.now());
        boolean flag = orderService.save(order);
        if (flag) {
            return new HttpResponseEntity(200, null, "添加成功!");
        } else {
            return new HttpResponseEntity(404, null, "添加失败!");
        }

    }

    /**
     * 对订单进行排序接口 0: 按日期从大到小排序 1: 按日期从小到大排序 2: 按金额从大到小排序 3: 按金额从小到大排序
     * @param type 排序类型
     * @param id 用户id
     * @return
     */
    @GetMapping("{id}/{type}")
    public HttpResponseEntity sortByType(@PathVariable("type") int type, @PathVariable("id") Long id) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, id);
        switch (type){
            case 0:
                wrapper.orderBy(true, false, Order::getCreateDate);
                break;
            case 1:
                wrapper.orderBy(true, true, Order::getCreateDate);
                break;
            case 2:
                wrapper.orderBy(true, false, Order::getTotal);
                break;
            case 3:
                wrapper.orderBy(true, true, Order::getTotal);
                break;
        }

        List<Order> orders = orderService.list(wrapper);

        return new HttpResponseEntity(200, orders, "查询成功!");
    }

}

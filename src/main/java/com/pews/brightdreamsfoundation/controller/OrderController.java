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
        wrapper.eq(Order::getId, id);
        List<Order> orders = orderService.list(wrapper);

        return new HttpResponseEntity(200, orders, "查询成功!");
    }

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
}

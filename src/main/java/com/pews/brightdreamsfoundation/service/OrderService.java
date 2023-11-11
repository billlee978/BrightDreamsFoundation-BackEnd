package com.pews.brightdreamsfoundation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pews.brightdreamsfoundation.beans.Order;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderService extends IService<Order> {
    void fillGoodToOrder(List<Order> orders);
}

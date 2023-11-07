package com.pews.brightdreamsfoundation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pews.brightdreamsfoundation.beans.Good;
import com.pews.brightdreamsfoundation.beans.Mission;
import com.pews.brightdreamsfoundation.beans.Order;
import com.pews.brightdreamsfoundation.beans.User;

import java.util.List;

public interface GoodService extends IService<Good> {
    void buyGoods(Good good, User user, Order order);

}

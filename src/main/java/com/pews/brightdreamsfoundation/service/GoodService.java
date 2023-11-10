package com.pews.brightdreamsfoundation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pews.brightdreamsfoundation.beans.*;

import java.util.List;

/**
 * 商品service接口
 */
public interface GoodService extends IService<Good> {
    void buyGoods(Good good, User user, Order order);

    List<Good> sortGoods();

    boolean releaseGood(Long id);
}

package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.Good;
import com.pews.brightdreamsfoundation.beans.MissionHistory;
import com.pews.brightdreamsfoundation.mapper.GoodMapper;
import com.pews.brightdreamsfoundation.mapper.MissionHistoryMapper;
import com.pews.brightdreamsfoundation.service.GoodService;
import com.pews.brightdreamsfoundation.service.OrderService;
import com.pews.brightdreamsfoundation.service.PointHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good>implements GoodService {
    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private PointHistoryService pointHistoryService;

    @Autowired
    private OrderService orderService;

    /**
     * 逻辑1：减少库存
     * 逻辑2：减少积分
     * 逻辑3：增加减少积分的记录
     * 逻辑4：创建购买订单
     */
    public void buyGoods(Good good){
        //减少库存并更新数据库
        good.setStock(good.getStock() - 1);
        goodMapper.updateById(good);

        //减少积分并更新数据库


        //增加减少积分的记录


        //创建购买订单

    }


}

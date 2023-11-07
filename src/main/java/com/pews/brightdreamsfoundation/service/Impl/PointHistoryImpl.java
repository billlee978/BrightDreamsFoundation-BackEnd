package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.*;
import com.pews.brightdreamsfoundation.mapper.PointHistoryMapper;
import com.pews.brightdreamsfoundation.service.GoodService;
import com.pews.brightdreamsfoundation.service.PointHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PointHistoryImpl extends ServiceImpl<PointHistoryMapper, PointHistory> implements PointHistoryService {
    @Autowired
    private GoodService goodService;
    @Override
    public boolean addPointHistory(User user, Mission mission) {
        PointHistory pointHistory = new PointHistory();
        pointHistory.setChangeDate(LocalDateTime.now());
        pointHistory.setChange((long) mission.getReward());
        pointHistory.setUserId(user.getId());
        String description = "完成任务\"" + mission.getMissionName() + "\" 奖励积分" + mission.getReward() + "分";
        pointHistory.setDescription(description);

        return save(pointHistory);
    }

    @Override
    public boolean addPointHistory(User user, Order order) {
        Good good = goodService.getById(order.getId());
        PointHistory pointHistory = new PointHistory();
        pointHistory.setChangeDate(order.getCreateDate());
        pointHistory.setChange(-1 * order.getTotal());
        pointHistory.setUserId(user.getId());
        pointHistory.setDescription("兑换商品  " + good.getGoodName() + "x" + order.getAmount() + " 消耗积分" + order.getTotal() + "分");
        return save(pointHistory);
    }
}

package com.pews.brightdreamsfoundation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pews.brightdreamsfoundation.beans.Mission;
import com.pews.brightdreamsfoundation.beans.Order;
import com.pews.brightdreamsfoundation.beans.PointHistory;
import com.pews.brightdreamsfoundation.beans.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


public interface PointHistoryService extends IService<PointHistory> {
    boolean addPointHistory(User user, Mission mission);

    boolean addPointHistory(User user, Order order);

    List<PointHistory> searchPointHistory(String keywords, Long id);

}

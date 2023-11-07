package com.pews.brightdreamsfoundation;

import com.pews.brightdreamsfoundation.beans.Order;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.service.PointHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Types;
import java.util.Collections;

@SpringBootTest
public class MapperGeneratorTest {
    @Autowired
    PointHistoryService pointHistoryService;
    @Test
    public void GenerateMapper() {}

    @Test
    public void testAddPointHistory() {
        Order order = new Order();
        order.setGoodId(1L);
        order.setUserId(17L);
        order.setAmount(2);
        order.setTotal(100L);

        User user = new User();
        user.setId(17L);

        pointHistoryService.addPointHistory(user, order);
    }
}

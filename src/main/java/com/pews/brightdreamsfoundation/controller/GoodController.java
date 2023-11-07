package com.pews.brightdreamsfoundation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pews.brightdreamsfoundation.beans.Good;
import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import com.pews.brightdreamsfoundation.beans.Order;
import com.pews.brightdreamsfoundation.beans.User;
import com.pews.brightdreamsfoundation.service.GoodService;
import com.pews.brightdreamsfoundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

@RestController
@RequestMapping("good")
public class GoodController {
    @Autowired
    private GoodService goodService;
    @Autowired
    private UserService userService;

    private final ReentrantLock buyLock = new ReentrantLock();

    /**
     * 获取所有商品
     */
    @GetMapping
    public HttpResponseEntity getAllGoods() {
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
        List<Good> goodList = goodService.list(wrapper);
        if (goodList.size() == 0) {
            return new HttpResponseEntity(404, null, "暂无可上架的商品");
        } else {
            return new HttpResponseEntity(200, goodList, "查询成功");
        }
    }

    /**
     * 获取可以上架的商品
     */
    @PostMapping("enable")
    public HttpResponseEntity getEnableGoods() {
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Good::isOnSale, 1);
        List<Good> goodList = goodService.list(wrapper);
        if (goodList.size() == 0) {
            return new HttpResponseEntity(404, null, "暂无可上架的商品");
        } else {
            return new HttpResponseEntity(200, goodList, "查询成功");
        }
    }

    /**
     * 搜索商品（支持模糊搜索）
     */
    @PostMapping("search")
    public HttpResponseEntity searchGoods(String keywords) {
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();

        //模糊查询
        wrapper.like(Good::getGoodName, keywords).or().like(Good::getDescription, keywords);
        List<Good> goodList = goodService.list(wrapper);
        if (goodList.size() == 0) {
            return new HttpResponseEntity(404, null, "暂无该商品");
        } else {
            return new HttpResponseEntity(200, goodList, "查询成功");
        }
    }

    /**
     * 兑换商品
     * <p>
     * 当有一个线程进入时，会上锁，此时如果有其他线程进入，将阻塞该线程，直到上一线程执行完毕释放解锁
     */

    @PostMapping("buy")
    public HttpResponseEntity buyGoods(@RequestBody Order order) {

        try {
            //上锁
            buyLock.lock();

            LambdaQueryWrapper<Good> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Good::getId, order.getGoodId());
            Good good = goodService.getOne(queryWrapper);

            LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(User::getId, order.getUserId());
            User user = userService.getOne(queryWrapper1);

            if (good == null) {
                return new HttpResponseEntity(404, null, "商品不存在");
            }

            if (user == null) {
                return new HttpResponseEntity(404, null, "用户不存在");
            }

            //检查库存
            if (good.getStock() <= 0) {
                return new HttpResponseEntity(404, null, "商品已售罄");
            }
            if (good.getStock() - order.getAmount() <= 0) {
                return new HttpResponseEntity(404, null, "商品不足");
            }

            //检查积分是否足够
            if (user.getPoints() < order.getTotal()) {
                return new HttpResponseEntity(404, null, "积分不足");
            }

            try {
                goodService.buyGoods(good, user, order);
                return new HttpResponseEntity(200, null, "购买成功");
            } catch (Exception e) {
                return new HttpResponseEntity(404, null, "购买失败");
            }
        } finally {
            //解锁
            buyLock.unlock();
        }
    }


    /**
     * 返回某一商品的库存数量
     */
    public Integer getGoodStock(Good good) {
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Good::getGoodName, good.getGoodName());
        List<Good> goodList = goodService.list(wrapper);
        return goodList.size();
    }

}

package com.pews.brightdreamsfoundation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pews.brightdreamsfoundation.beans.Good;
import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import com.pews.brightdreamsfoundation.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("good")
public class GoodController {
    @Autowired
    private GoodService goodService;

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
        wrapper.like(Good::getGoodName, keywords);
        List<Good> goodList = goodService.list(wrapper);
        if (goodList.size() == 0) {
            return new HttpResponseEntity(404, null, "暂无该商品");
        } else {
            return new HttpResponseEntity(200, goodList, "查询成功");
        }
    }

    /**
     * 兑换商品
     *
     * 当有一个线程进入时，会上锁，此时如果有其他线程进入，将阻塞该线程，直到上一线程执行完毕释放解锁
     */

    @PostMapping("buy")
    public HttpResponseEntity buyGoods(@RequestBody Good good) {

        try {
            //上锁
            buyLock.lock();

            LambdaQueryWrapper<Good> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Good::getId, good.getId());
            Good dbGood = goodService.getOne(queryWrapper);

            if (dbGood == null) {
                return new HttpResponseEntity(404, null, "商品不存在");
            }

            //检查库存
            if (dbGood.getStock() <= 0) {
                return new HttpResponseEntity(404, null, "商品已售罄");
            }

            //购买商品
            goodService.buyGoods(dbGood);

            return new HttpResponseEntity(200, null, "购买成功");
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

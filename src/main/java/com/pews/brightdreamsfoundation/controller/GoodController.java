package com.pews.brightdreamsfoundation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pews.brightdreamsfoundation.beans.Good;
import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import com.pews.brightdreamsfoundation.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("good")
public class GoodController {
    @Autowired
    private GoodService goodService;

    /**
     * 获取所有商品
     */
    @GetMapping
    public HttpResponseEntity getAllGoods(){
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
        List<Good> goodList = goodService.list(wrapper);
        if(goodList.size()==0){
            return new HttpResponseEntity(404, null,"暂无可上架的商品");
        }
        else{
            return new HttpResponseEntity(200, goodList,"查询成功");
        }
    }

    /**
     * 获取可以上架的商品
     */
    @PostMapping("enable")
    public HttpResponseEntity getEnableGoods(){
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Good::isOnSale,1);
        List<Good> goodList = goodService.list(wrapper);
        if(goodList.size()==0){
            return new HttpResponseEntity(404, null,"暂无可上架的商品");
        }
        else{
            return new HttpResponseEntity(200, goodList,"查询成功");
        }
    }
}

package com.pews.brightdreamsfoundation.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pews.brightdreamsfoundation.beans.*;
import com.pews.brightdreamsfoundation.service.GoodService;
import com.pews.brightdreamsfoundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("good")
public class GoodController {
    @Autowired
    private GoodService goodService;
    @Autowired
    private UserService userService;

    @Autowired
    OSS ossClient;
    @Value("${aliyun.bucketName}")
    String bucketName;
    @Value("${aliyun.endpoint}")
    String endpoint;
    @Value("${aliyun.area}")
    String area;

    //创建商品锁，每个商品id对应一个锁
    private final ConcurrentHashMap<Long, ReentrantLock> goodLocks = new ConcurrentHashMap<>();


    /**
     * 获取所有商品
     */
    @GetMapping
    public HttpResponseEntity getAllGoods() {
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
        List<Good> goodList = goodService.list(wrapper);
        if (goodList.size() == 0) {
            return new HttpResponseEntity(404, null, "暂无商品");
        } else {
            return new HttpResponseEntity(200, goodList, "查询成功");
        }
    }

    /**
     * 根据热度获取所有可上架商品
     */
    @PostMapping("sortByPopularity")
    public HttpResponseEntity sortGoodByPurchaseCount() {
        List<Good> goodList = goodService.sortGoods();
        if (goodList.size() == 0) {
            return new HttpResponseEntity(404, null, "暂无商品");
        } else {
            return new HttpResponseEntity(200, goodList, "查询成功");
        }
    }

    /**
     * 根据积分获取所有可上架商品
     */
    @PostMapping("sortByCost{type}")
    public HttpResponseEntity sortGoodsByCost(@PathVariable Integer type) {

        if (type == 0) {
            //从小到大排序
            LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByAsc(Good::getCost);
            List<Good> goodList = goodService.list(wrapper);
            if(goodList.size()==0){
                return new HttpResponseEntity(404,null,"暂无商品");
            }
            else{
                return new HttpResponseEntity(200,goodList,"查询成功");
            }
        } else if (type == 1) {
            //从大到小排序
            LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(Good::getCost);
            List<Good> goodList = goodService.list(wrapper);
            if(goodList.size()==0){
                return new HttpResponseEntity(404,null,"暂无商品");
            }
            else{
                return new HttpResponseEntity(200,goodList,"查询成功");
            }
        }else{
            return new HttpResponseEntity(404,null,"参数有误");
        }
    }

    /**
     * 搜索商品（支持模糊搜索）
     */
    @PostMapping("search")
    public HttpResponseEntity searchGoods(String keywords) {
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();

        //模糊查询
        wrapper.eq(Good::isOnSale, 1).like(Good::getGoodName, keywords).or().like(Good::getDescription, keywords);
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
     * 不同商品之间可以并发处理，同一商品的购买请求会被锁定
     */

    @PostMapping("buy")
    public HttpResponseEntity buyGoods(@RequestBody Order order) {
        Long goodId = order.getGoodId();
        //根据商品id来创建对应的商品锁
        ReentrantLock goodLock = goodLocks.computeIfAbsent(goodId, k -> new ReentrantLock());

        try {
            //上锁
            goodLock.lock();

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
            if (good.getStock() - order.getAmount() < 0) {
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
            goodLock.unlock();
        }
    }

    @GetMapping("{page}/{limit}")
    public HttpResponseEntity getGoodPage(@PathVariable("page") Long page,
                                             @PathVariable("limit") Long limit,
                                             Good good) {
        IPage<Good> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
        IPage<Good> iPage = goodService.page(pageParam, wrapper);

        return new HttpResponseEntity(200, iPage, "OK!");
    }

    @PostMapping("save")
    public HttpResponseEntity saveGood(@RequestBody Good good) {
        good.setId(0L);
        return goodService.save(good) ? HttpResponseEntity.ok() : new HttpResponseEntity(500, null, "Failed!");
    }

    @RequestMapping("upload")
    public HttpResponseEntity uploadFile(MultipartFile file) {
        try {
            String[] fileOriginalName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
            String filename = "img/" + UUID.randomUUID() + "." + fileOriginalName[fileOriginalName.length - 1];
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, file.getInputStream());
            ossClient.putObject(putObjectRequest);
            String picture = "https://" + bucketName + "." + area + "/" + filename;

            return new HttpResponseEntity(200, picture, "OK");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("batchRemove")
    public HttpResponseEntity batchRemove(@RequestBody List<Long> idList) {
        return goodService.removeBatchByIds(idList) ? HttpResponseEntity.ok() : new HttpResponseEntity(500, null, "Failed!");
    }

    @PostMapping("releaseGood/{id}")
    public HttpResponseEntity releaseMission(@PathVariable("id") Long id) {
        return goodService.releaseGood(id) ? HttpResponseEntity.ok() : new HttpResponseEntity(500, null, "Failed!");
    }

    @DeleteMapping("remove/{id}")
    public HttpResponseEntity removeById(@PathVariable("id") Long id) {
        LambdaQueryWrapper<Good> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Good::getId, id);
        return goodService.remove(wrapper) ? HttpResponseEntity.ok() : new HttpResponseEntity(500, null, "Failed!");
    }
}

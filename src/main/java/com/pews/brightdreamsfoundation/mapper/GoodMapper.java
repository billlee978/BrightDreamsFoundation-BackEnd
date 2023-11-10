package com.pews.brightdreamsfoundation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pews.brightdreamsfoundation.beans.Good;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品mapper接口
 */
@Mapper
public interface GoodMapper extends BaseMapper<Good> {
    //商品排序
    List<Good> sortGoods();

    int releaseGood(Long id);
}

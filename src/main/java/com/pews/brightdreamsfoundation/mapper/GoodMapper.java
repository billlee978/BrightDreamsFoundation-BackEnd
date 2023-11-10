package com.pews.brightdreamsfoundation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pews.brightdreamsfoundation.beans.Good;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodMapper extends BaseMapper<Good> {
    List<Good> sortGoods();

    int releaseGood(Long id);
}

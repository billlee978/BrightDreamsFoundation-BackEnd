package com.pews.brightdreamsfoundation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pews.brightdreamsfoundation.beans.PointHistory;
import org.apache.ibatis.annotations.Mapper;

import java.awt.event.HierarchyBoundsAdapter;
import java.util.List;

@Mapper
public interface PointHistoryMapper extends BaseMapper<PointHistory> {
    List<PointHistory> selectByMonth(Long id, int month);

}

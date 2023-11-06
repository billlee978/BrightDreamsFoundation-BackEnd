package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.PointHistory;
import com.pews.brightdreamsfoundation.mapper.PointHistoryMapper;
import com.pews.brightdreamsfoundation.service.PointHistoryService;
import org.springframework.stereotype.Service;

@Service
public class PointHistoryImpl extends ServiceImpl<PointHistoryMapper, PointHistory> implements PointHistoryService {
}

package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.Good;
import com.pews.brightdreamsfoundation.beans.MissionHistory;
import com.pews.brightdreamsfoundation.mapper.GoodMapper;
import com.pews.brightdreamsfoundation.mapper.MissionHistoryMapper;
import com.pews.brightdreamsfoundation.service.GoodService;
import org.springframework.stereotype.Service;

@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good>implements GoodService {
}

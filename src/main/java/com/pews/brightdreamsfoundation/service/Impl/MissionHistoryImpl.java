package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.MissionHistory;
import com.pews.brightdreamsfoundation.mapper.MissionHistoryMapper;
import com.pews.brightdreamsfoundation.service.MissionHistoryService;
import org.springframework.stereotype.Service;

@Service
public class MissionHistoryImpl extends ServiceImpl<MissionHistoryMapper, MissionHistory> implements MissionHistoryService {
}

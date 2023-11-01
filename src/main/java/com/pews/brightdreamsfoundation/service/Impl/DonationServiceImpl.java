package com.pews.brightdreamsfoundation.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pews.brightdreamsfoundation.beans.Donation;
import com.pews.brightdreamsfoundation.mapper.DonationMapper;
import com.pews.brightdreamsfoundation.service.DonationService;
import org.springframework.stereotype.Service;

@Service
public class DonationServiceImpl extends ServiceImpl<DonationMapper, Donation> implements DonationService {
}

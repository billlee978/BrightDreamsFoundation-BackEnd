package com.pews.brightdreamsfoundation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pews.brightdreamsfoundation.beans.Donation;
import com.pews.brightdreamsfoundation.beans.HttpResponseEntity;
import com.pews.brightdreamsfoundation.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 捐赠记录controller
 */
@RestController
@RequestMapping("donation")
public class DonationController {
    @Autowired
    DonationService donationService;

    /**
     * 捐赠记录分页
     * @param page
     * @param limit
     * @param donation
     * @return
     */
    @GetMapping("{page}/{limit}")
    public HttpResponseEntity getDonationPage(@PathVariable("page") Long page,
                                              @PathVariable("limit") Long limit,
                                              Donation donation) {
        IPage<Donation> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<Donation> wrapper = new LambdaQueryWrapper<>();
        if (donation.getDonationId() != null) {
            wrapper.eq(Donation::getDonationId, donation.getDonationId());
        }
        if (donation.getDonatorId() != null && donation.getDonatorId() != 0L) {
            wrapper.eq(Donation::getDonatorId, donation.getDonatorId());
        }
        IPage<Donation> iPage = donationService.page(pageParam, wrapper);

        return new HttpResponseEntity(200, iPage, "OK!");
    }
}

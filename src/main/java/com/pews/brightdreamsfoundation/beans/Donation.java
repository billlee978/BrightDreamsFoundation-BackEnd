package com.pews.brightdreamsfoundation.beans;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("`t_donation`")
public class Donation {
    @TableId
    Long id;
    @TableField("DONATION_ID")
    UUID donationId;
    @TableField("DONATE_ITEM")
    String donationItem;
    @TableField("DONATE_ACCEPT")
    String donateAccept;
    @TableField("SEND_LOCATION")
    String sendLocation;
    @TableField("ACCEPT_LOCATION")
    String acceptLocation;
    @TableField("CURRENT_LOCATION")
    String currentLocation;
    @TableField("EST_ARRIVE_TIME")
    LocalDateTime estArriveTime;
    @TableField("DONATE_DESCRIPTION")
    String donateDescription;
}

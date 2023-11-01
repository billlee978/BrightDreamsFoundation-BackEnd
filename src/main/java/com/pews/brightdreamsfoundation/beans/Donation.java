package com.pews.brightdreamsfoundation.beans;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    String donationId;
    @TableField("DONATOR_ID")
    Long donatorId;
    @TableField("DONATOR_NAME")
    String donatorName;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime estArriveTime;
    @TableField("DONATE_DESCRIPTION")
    String donateDescription;
}

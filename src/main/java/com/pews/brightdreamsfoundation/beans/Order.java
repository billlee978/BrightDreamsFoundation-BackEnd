package com.pews.brightdreamsfoundation.beans;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("`t_order`")
public class Order {
    @TableId(type = IdType.AUTO)
    Long id;
    @TableField(value = "USER_ID")
    Long userId;
    @TableField(value = "GOOD_ID")
    Long goodId;
    @TableField(value = "AMOUNT")
    Long amount;
    @TableField(value = "TOTAL")
    Long total;
    @TableField(value = "CREATEDATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime createDate;
    @TableLogic(delval = "1")
    byte isDeleted;
}

package com.pews.brightdreamsfoundation.beans;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 商品bean类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("`t_good`")
public class Good {
    @TableId(type = IdType.AUTO)
    Long id;
    @TableField(value = "GOOD_NAME")
    String goodName;
    @TableField(value = "DESCRIPTION")
    String description;
    @TableField(value = "STOCK")
    int stock;
    @TableField(value = "COST")
    int cost;
    @TableField(value = "PICTURE")
    String picture;
    @TableField(value = "ON_SALE")
    boolean onSale;
    @TableLogic(delval = "1")
    byte isDeleted;

}

package com.pews.brightdreamsfoundation.beans;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("`t_good`")
public class Good {
    @TableId
    Long id;
    @TableField(value = "GOODNAME")
    String goodName;
    @TableField(value = "DESCRIPTION")
    String description;
    @TableField(value = "STOCK")
    int stock;
    @TableField(value = "COST")
    int cost;
    @TableField(value = "PICTURE")
    String picture;
    @TableLogic(delval = "1")
    byte isDeleted;

}

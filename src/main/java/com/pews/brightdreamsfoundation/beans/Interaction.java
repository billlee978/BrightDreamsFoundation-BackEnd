package com.pews.brightdreamsfoundation.beans;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Content;
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
@TableName("`t_interaction")
public class Interaction {
    @TableId
    Long id;
    @TableField(value = "CHILDREN_ID")
    Long childrenId;
    @TableField(value = "VOLUNTEER_ID")
    Long volunteerId;
    @TableField(value = "TYPE", jdbcType = JdbcType.TINYINT)
    byte type;
    @TableField(value = "INTERACT_TIME")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime interactTime;
    @TableField(value = "AMOUNT")
    int amount;
    @TableField(value = "CONTENT")
    String content;
    @TableField(value = "PICTURE_URL")
    String pictureURL;
}

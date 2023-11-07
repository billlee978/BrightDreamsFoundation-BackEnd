package com.pews.brightdreamsfoundation.beans;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("`t_point_history`")
public class PointHistory{
    @TableId(type = IdType.AUTO)
    Long id;
    @TableField(value = "USER_ID")
    Long userId;
    @TableField(value = "`CHANGE`", jdbcType = JdbcType.BIGINT)
    Long change;
    @TableField(value = "DESCRIPTION")
    String description;
    @TableField(value = "CHANGE_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime changeDate;
}

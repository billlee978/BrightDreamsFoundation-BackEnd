package com.pews.brightdreamsfoundation.beans;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("`t_mission`")
public class Mission {
    @TableId
    Long id;
    @TableField(value = "MISSION_NAME")
    String missionName;
    @TableField(value = "DESCRIPTION")
    String description;
    @TableField(value = "KIND", jdbcType = JdbcType.TINYINT)
    byte kind;
    @TableField(exist = false)
    String kindName;
    @TableField(value = "TARGET_NUM")
    int targetNum;
    @TableField(value = "REWARD")
    int reward;
    @TableField(value = "DEADLINE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime deadline;
    @TableField(value = "RELEASE_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime releaseDate;
    @TableLogic(delval = "1")
    byte isDeleted;
    @TableField(value = "PICTURE_URL")
    String pictureURL;
    @TableField(value = "IS_RELEASED", jdbcType = JdbcType.BOOLEAN)
    boolean isReleased;
}

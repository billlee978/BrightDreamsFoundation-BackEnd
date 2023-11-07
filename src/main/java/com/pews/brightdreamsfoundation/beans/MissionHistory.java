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

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("`t_mission_history`")
public class MissionHistory {
    @TableId(type = IdType.AUTO)
    Long id;
    @TableField(value = "USER_ID")
    Long userId;
    @TableField(value = "MISSION_ID")
    Long missionId;
    @TableField(value = "FINISH_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime finishDate;
    @TableField(value = "SUBMISSION_URL")
    String submissionURL;
    @TableField(value = "STATUS", jdbcType = JdbcType.TINYINT) // 0: 审核 1:通过 2:拒绝
    Byte status;
    @TableField(value = "COMMENT")
    String comment;
    @TableField(value = "DESCRIPTION")
    String description;
    @TableField(value = "RATE")
    int rate;
    @TableField(exist = false)
    Mission mission;
}

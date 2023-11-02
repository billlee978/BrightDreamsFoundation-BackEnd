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

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("`t_mission_history`")
public class MissionHistory {
    @TableId
    Long id;
    @TableField(value = "USER_ID")
    Long userId;
    @TableField(value = "MISSION_ID")
    Long missionId;
    @TableField(value = "FINISH_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime finishDate;
}
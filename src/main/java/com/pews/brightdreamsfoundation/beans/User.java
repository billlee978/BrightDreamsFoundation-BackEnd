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
@TableName("`t_user`")
public class User {
    @TableId(type = IdType.AUTO)
    Long id;
    @TableField(value = "USERNAME")
    String username;
    @TableField(value = "PASSWORD")
    String password;
    @TableField(value = "ROLE", jdbcType = JdbcType.TINYINT)
    byte role;
    @TableField(exist = false)
    String roleName;
    @TableField(value = "REALNAME")
    String realName;
    @TableField(value = "SCHOOL")
    String school;
    @TableField(value = "CLAZZ")
    String clazz;
    @TableField(value = "POINTS", jdbcType = JdbcType.BIGINT)
    Long points;
    @TableField(value = "AGE", jdbcType = JdbcType.TINYINT)
    byte age;
    @TableField(value = "GENDER", jdbcType = JdbcType.TINYINT)
    byte gender;
    @TableField(exist = false)
    String genderStr;
    @TableField(value = "CREATEDATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    LocalDateTime createDate;
    @TableLogic(delval = "1")
    byte isDeleted;
}

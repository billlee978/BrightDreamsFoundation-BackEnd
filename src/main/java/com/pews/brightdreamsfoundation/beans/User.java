package com.pews.brightdreamsfoundation.beans;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableId
    Long id;
    @TableField(value = "USERNAME")
    String username;
    @TableField(value = "PASSWORD")
    String password;
    @TableField(value = "ROLE", jdbcType = JdbcType.TINYINT)
    byte role;
    @TableField(exist = false)
    String roleName;
    @TableField(value = "CREATEDATE")
    LocalDateTime createDate;
    @TableLogic(delval = "1")
    byte isDeleted;
}

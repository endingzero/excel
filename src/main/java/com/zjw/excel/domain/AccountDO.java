package com.zjw.excel.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: VisitLogDO
 * @Description: 拜访新表
 * @author: jiewei
 * @date: 2019/8/16
 */
@TableName("app_account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 是否主账号
     */
    private Boolean master;

    /**
     * 是否激活
     */
    private Boolean activated;

    /**
     * 是否激活
     */
    private Date createTime;

    /**
     * 是否激活
     */
    private Date updateTime;

    /**
     * 是否修改过密码
     */
    private Boolean updatePwd;

}

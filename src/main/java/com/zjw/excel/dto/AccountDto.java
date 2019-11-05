package com.zjw.excel.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: AccountDto
 * @Description:
 * @author: jiewei
 * @date: 2019/11/5
 */
@Data
public class AccountDto {
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

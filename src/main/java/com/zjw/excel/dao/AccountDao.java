package com.zjw.excel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjw.excel.domain.AccountDO;
import com.zjw.excel.dto.AccountDto;
import com.zjw.excel.excel.handler.ExcelResultHandler;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName: AccountDao
 * @Description:
 * @author: jiewei
 * @date: 2019/11/5
 */
public interface AccountDao extends BaseMapper<AccountDO> {

    @ResultType(value=AccountDto.class)
    @Select("select * from app_account where activated = 1")
    void export(ExcelResultHandler resultHandler);
    @Select("select * from app_account where activated = 1")
    List<AccountDto> export();
}

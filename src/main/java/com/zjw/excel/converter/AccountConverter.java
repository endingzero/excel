package com.zjw.excel.converter;

import com.zjw.excel.dto.AccountDto;
import com.zjw.excel.excel.converter.ExcelDataConverter;
import com.zjw.excel.util.DateUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: AccountConverter
 * @Description: 账户导出数据转换
 * @author: jiewei
 * @date: 2019/9/25
 */
public class AccountConverter implements ExcelDataConverter<AccountDto> {

    @Getter
    private String fileName;

    public AccountConverter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public String getSheetName() {
        return "用户数据";
    }

    @Override
    public List<String> getTitle() {
        List<String> titles = new ArrayList<>();
        titles.add("手机");
        titles.add("密码");
        titles.add("密码盐值");
        titles.add("是否主账号");
        titles.add("是否激活");
        titles.add("创建时间");
        titles.add("更新时间");
        titles.add("是否修改过密码");
        return titles;
    }


    /**
     * 数据转换
     * @param data
     * @return
     */
    @Override
    public List<String> apply(AccountDto data) {
        List<String> dataList = new ArrayList<>();
        dataList.add(data.getMobile());
        dataList.add(data.getPassword());
        dataList.add(data.getSalt());
        dataList.add(data.getMaster() ? "是" : "否");
        dataList.add(data.getActivated() ? "是" : "否");
        dataList.add(data.getCreateTime() != null ? DateUtils.format(data.getCreateTime(),DateUtils.DATE_YYYY_MM_DD) : "");
        dataList.add(data.getUpdateTime() != null ? DateUtils.format(data.getUpdateTime(),DateUtils.DATE_YYYY_MM_DD) : "");
        dataList.add(data.getUpdatePwd() ? "是" : "否");
        return dataList;
    }
}

package com.zjw.excel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjw.excel.converter.AccountConverter;
import com.zjw.excel.dao.AccountDao;
import com.zjw.excel.domain.AccountDO;
import com.zjw.excel.dto.AccountDto;
import com.zjw.excel.excel.ExcelData;
import com.zjw.excel.excel.handler.ExcelResultHandler;
import com.zjw.excel.service.AccountService;
import com.zjw.excel.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: AccountServiceImpl
 * @Description:
 * @author: jiewei
 * @date: 2019/11/5
 */
@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountDao, AccountDO> implements AccountService {


    @Override
    public ExcelData oldExport() {
        long begin = System.currentTimeMillis();
        List<AccountDto> accountDtoList = baseMapper.export();
        long end = System.currentTimeMillis();
        log.info("旧查询消耗时间:"+(end-begin) + "ms");
        ExcelData excelData = new ExcelData();
        List<String> titles = new ArrayList<>();
        titles.add("手机");
        titles.add("密码");
        titles.add("密码盐值");
        titles.add("是否主账号");
        titles.add("是否激活");
        titles.add("创建时间");
        titles.add("更新时间");
        titles.add("是否修改过密码");
        excelData.setName("发放优惠券明细");
        excelData.setTitles(titles);
        if (!accountDtoList.isEmpty()) {
            List<List<Object>> exportData = new ArrayList<>(accountDtoList.size());
            accountDtoList.forEach(data -> {
                List<Object> dataList = new ArrayList<>();
                dataList.add(data.getMobile());
                dataList.add(data.getPassword());
                dataList.add(data.getSalt());
                dataList.add(data.getMaster() ? "是" : "否");
                dataList.add(data.getActivated() ? "是" : "否");
                dataList.add(data.getCreateTime() != null ? DateUtils.format(data.getCreateTime(),DateUtils.DATE_YYYY_MM_DD) : "");
                dataList.add(data.getUpdateTime() != null ? DateUtils.format(data.getUpdateTime(),DateUtils.DATE_YYYY_MM_DD) : "");
                dataList.add(data.getUpdatePwd() ? "是" : "否");
                exportData.add(dataList);
            });
            excelData.setRows(exportData);
        }
        return excelData;
    }

    /**
     * //        List<AccountDto> accountDtoList = baseMapper.export();
     * //        resultHandler.setDataCache(accountDtoList);
     * //        resultHandler.handlerData();
     * //        resultHandler.clearDataCache();
     * @return
     */
    @Override
    public ResponseEntity<FileSystemResource> export() {
        String fileName = "用户数据";
        ExcelResultHandler resultHandler = ExcelResultHandler.create(new AccountConverter(fileName));
        baseMapper.export(resultHandler);
        return resultHandler.get();
    }
}

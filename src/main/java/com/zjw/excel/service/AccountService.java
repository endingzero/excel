package com.zjw.excel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zjw.excel.domain.AccountDO;
import com.zjw.excel.excel.ExcelData;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

/**
 * @ClassName: AccountService
 * @Description:
 * @author: jiewei
 * @date: 2019/11/5
 */
public interface AccountService extends IService<AccountDO> {


    ExcelData oldExport();

    ResponseEntity<FileSystemResource> export();
}

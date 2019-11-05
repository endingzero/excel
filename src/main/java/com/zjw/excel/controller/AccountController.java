package com.zjw.excel.controller;

import com.zjw.excel.excel.ExcelData;
import com.zjw.excel.service.AccountService;
import com.zjw.excel.util.ExportUtils;
import com.zjw.excel.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


/**
 * @ClassName:
 * @Description:
 * @author: jiewei
 * @date: 2019/11/5
 */
@RequestMapping("/app/account")
@RestController
@Api(value = "/app/account",tags = "用户")
public class AccountController  {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "导出用户数据", notes = "")
    @ResponseBody
    @GetMapping("/old/export")
    public Result<Object> oldExport(HttpServletResponse response) {
        String fileName = "用户数据.xlsx";
        ExcelData excelData = accountService.oldExport();
        ExportUtils.exportExcel(response, fileName, excelData);
        return Result.ok();
    }


    @GetMapping("/new/export")
    @ApiOperation(value = "导出全部用户",produces = "application/octet-stream")
    public ResponseEntity<FileSystemResource> export() {
        return this.accountService.export();
    }

}

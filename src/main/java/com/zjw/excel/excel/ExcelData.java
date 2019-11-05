package com.zjw.excel.excel;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wanglei
 * Date: 2019/4/11
 * Time: 10:16
 * Compay: yishou
 * Description:
 */
@Data
public class ExcelData implements Serializable {

    private static final long serialVersionUID = 7798190956693155473L;
    // 表头
    private List<String> titles;

    // 数据
    private List<List<Object>> rows;

    // 页签名称
    private String name;
}
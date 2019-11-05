package com.zjw.excel.excel.converter;


import java.util.List;
import java.util.function.Function;

/**
 * @ClassName: ExcelDataConverter
 * @Description: excel数据转换(将查询出来的数据进行处理)
 * @author: jiewei
 * @date: 2019/9/25
 */
public interface ExcelDataConverter<T> extends Function<T, List<String>> {

    /**
     *  因为apply方法是每次数据返回都会转换的
     *  所以建议不要使用mysql查询、redis查询、连接其他服务器等长时间的操作
     *  可以参考VisitLogConverter类
     */

    /**
     * 获取文件名字
     *
     * @return
     */
    String getFileName();

    /**
     * 获取sheet名字
     *
     * @return
     */
    String getSheetName();

    /**
     * 获取excel表头
     *
     * @return
     */
    List<String> getTitle();

}

package com.zjw.excel.excel.handler;


import com.zjw.excel.excel.converter.ExcelDataConverter;
import com.zjw.excel.excel.writer.ExcelDataWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @ClassName: ExcelResultHandler
 * @Description: 导出结果处理Handler
 * @author: jiewei
 * @date: 2019/9/25
 */
@Slf4j
public class ExcelResultHandler<T> implements ResultHandler<T>, Supplier<ResponseEntity<FileSystemResource>> {

    private List<T> dataCache;

    private ExcelDataConverter<T> dataConverter;

    private ExcelDataWriter dataWriter;

    private static final Integer CACHE_SIZE = 1000;

    /**
     * 在dao层的方法返回参数必须是void
     * 必须增加@ResultType(value=VisitManagementResponseDto.class)
     *
     * @param dataConverter
     * @param <T>
     * @return
     */
    public static <T> ExcelResultHandler create(ExcelDataConverter<T> dataConverter) {
        return new ExcelResultHandler(dataConverter);
    }

    public ExcelResultHandler(ExcelDataConverter<T> dataConverter) {
        this.dataWriter = new ExcelDataWriter();
        this.dataConverter = dataConverter;
        this.dataCache = new ArrayList<>(CACHE_SIZE);
        // 初始化dataWriter
        this.dataWriter.setFileName(dataConverter.getFileName());
        this.dataWriter.setTitle(dataConverter.getTitle());
        this.dataWriter.setSheetName(dataConverter.getSheetName());
    }

    private ExcelResultHandler() {
    }

    /**
     * 流式查询每一次都会调用此方法
     * 到了1000条才进行写入
     *
     * @param resultContext
     */
    @Override
    public void handleResult(ResultContext<? extends T> resultContext) {

        dataCache.add(resultContext.getResultObject());
        // 超过缓存个数才进行操作
        if (dataCache.size() == CACHE_SIZE) {
            this.handle();
        }
    }

    private void handle() {
        if (!dataCache.isEmpty()) {
            if (CACHE_SIZE.equals(dataCache.size())) {
                this.handlerData();
            }
            dataCache.clear();
        }
    }

    private void handlerData() {
        dataCache.forEach(v -> {
            // 将结果进行转换
            List<String> converterAfterData = dataConverter.apply(v);
            // 将结果进行写入到excel
            dataWriter.accept(converterAfterData);
        });
    }

    @Override
    public ResponseEntity<FileSystemResource> get() {
        // 最后一批的处理
        this.handlerData();
        return this.dataWriter.get();
    }
}

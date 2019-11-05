package com.zjw.excel.excel.writer;

import com.google.common.net.UrlEscapers;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @ClassName: ExcelDataWriter
 * @Description: excel数据写入, 转换成
 * @author: jiewei
 * @date: 2019/9/25
 */
@Slf4j
public class ExcelDataWriter implements Consumer<List<String>>, Supplier<ResponseEntity<FileSystemResource>> {

    private Workbook workbook;

    private Sheet sheet;

    private CellStyle titleCellStyle;

    private CellStyle dataCellStyle;

    //private AtomicInteger rowIndex;

    private Integer rowNumber;

    @Setter
    private String fileName;
    @Setter
    private List<String> title;
    @Setter
    private String sheetName;

    public ExcelDataWriter() {
        // 内存中限制行数，避免内存读取太多造成OOM,
        this.workbook = new SXSSFWorkbook(1000);
        //this.rowIndex = new AtomicInteger(0);
        // 初始化样式
        this.initStyle();
    }

    private void initStyle() {
        this.initTitleCellStyle();
        this.initDataCellStyle();
    }

    private void initDataCellStyle() {
        this.dataCellStyle = workbook.createCellStyle();
        Font dataFont = workbook.createFont();
        dataFont.setFontName("simsun");
        dataFont.setColor(IndexedColors.BLACK.index);
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleCellStyle.setFont(dataFont);
        titleCellStyle.setBorderTop(BorderStyle.THIN);
        titleCellStyle.setBorderLeft(BorderStyle.THIN);
        titleCellStyle.setBorderRight(BorderStyle.THIN);
        titleCellStyle.setBorderBottom(BorderStyle.THIN);
        titleCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        titleCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        titleCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        titleCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    }


    private void initTitleCellStyle() {
        this.titleCellStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setFontName("simsun");
        titleFont.setBold(true);
        titleFont.setColor(IndexedColors.BLACK.index);
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleCellStyle.setFont(titleFont);
        titleCellStyle.setBorderTop(BorderStyle.THIN);
        titleCellStyle.setBorderLeft(BorderStyle.THIN);
        titleCellStyle.setBorderRight(BorderStyle.THIN);
        titleCellStyle.setBorderBottom(BorderStyle.THIN);
        titleCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        titleCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        titleCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        titleCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    }

    /**
     * 将转换后的数据消耗掉写入workbook
     *
     * @param data 数据
     */
    @Override
    public void accept(List<String> data) {
        if (sheet == null) {
            // 写表头
            this.initSheet();
        }
        // 写行
        this.writeRow(data, dataCellStyle);
    }


    private void initSheet() {
        this.sheet = workbook.createSheet(sheetName);
        if (!title.isEmpty()) {
            rowNumber = 0;
            this.writeRow(title, titleCellStyle);
        }
    }

    private void writeRow(List<String> rowData, CellStyle cellStyle) {
        //Row row = this.synchronizedCreateRow(rowIndex.getAndIncrement());
        Row row = this.sheet.createRow(rowNumber);
        int i = 0;
        for (int length = rowData.size(); i < length; ++i) {
            //null的不用进行设置，""会进行设置，会占用文本大小
            if (rowData.get(i) != null) {
                //this.synchronizedCreateCell(row, i, rowData.get(i), cellStyle);
                CellUtil.createCell(row, i, rowData.get(i), cellStyle);
            }
        }
        ++this.rowNumber;
    }

    /**
     * sheet的row使用treeMap存储的，是非线程安全的，所以在创建row时需要进行同步操作。
     *
     * @param rowIndex
     * @return
     */
    private synchronized Row synchronizedCreateRow(int rowIndex) {
        return this.sheet.createRow(rowIndex);
    }

    private synchronized Cell synchronizedCreateCell(Row row, int i, String cellData, CellStyle cellStyle) {
        //return CellUtil.createCell(row, i, cellData, cellStyle);
        Cell cell = row.createCell(i);
        cell.setCellValue(cellData);
        return cell;
    }

    @Override
    public ResponseEntity<FileSystemResource> get() {
        File file = this.getFile(workbook);
        return ResponseEntity.ok()
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Content-Disposition", String.format("attachment; filename=%s.xlsx", UrlEscapers.urlPathSegmentEscaper().escape(fileName)))
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(file));
    }

    private File getFile(Workbook workbook) {

        File file = this.createTempFile();

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            workbook.write(fileOutputStream);
        } catch (Exception e) {
            log.error("导出错误excel", e);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                log.error("关闭workbook", e);
            }
        }
        return file;
    }

    private File createTempFile() {
        String prefix = UUID.randomUUID().toString();
        String suffix = ".xlsx";
        File file = null;
        try {
            file = File.createTempFile(prefix, suffix);
        } catch (IOException e) {
            log.error("创建临时文件", e);
        }
        return file;
    }
}

package com.wzx.demo.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.wzx.demo.pojo.NetWorth;
import com.wzx.demo.pojo.TitleHandler;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.File;
import java.util.List;

public class ExcelUtil {

    public static final String path =
            System.getProperty("user.dir") + "/" + DateUtil.getCurrentTime() + ".xlsx";

    public static void creatTableAndSave(List<NetWorth> list) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 14);
        headWriteFont.setFontName("等线");
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.write(file, NetWorth.class).sheet("指标统计");
        excelWriterSheetBuilder.registerWriteHandler(new TitleHandler());
        excelWriterSheetBuilder.doWrite(list);

    }


}

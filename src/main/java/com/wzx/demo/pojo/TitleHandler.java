package com.wzx.demo.pojo;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.*;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

public class TitleHandler implements CellWriteHandler {
    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, CellData cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

        try {

                // 设置列宽
                Sheet sheet = writeSheetHolder.getSheet();
                sheet.setColumnWidth(cell.getColumnIndex(), 14 * 256);
                writeSheetHolder.getSheet().getRow(0).setHeight((short) (1.8 * 256));
                Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
                // 设置标题字体样式
                WriteCellStyle headWriteCellStyle = new WriteCellStyle();
                WriteFont headWriteFont = new WriteFont();
                headWriteFont.setFontName("等线");
                headWriteFont.setFontHeightInPoints((short) 14);
                headWriteFont.setBold(true);
            if (cell.getColumnIndex() >3) {
                if (NumberFormat.getNumberInstance().parse(cellData.getStringValue()).doubleValue() < 0) {
                    headWriteFont.setColor(IndexedColors.GREEN.getIndex());
                } else {
                    headWriteFont.setColor(IndexedColors.RED.getIndex());
                }
            }
                headWriteCellStyle.setWriteFont(headWriteFont);
                /*headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());*/
                CellStyle cellStyle = StyleUtil.buildHeadCellStyle(workbook, headWriteCellStyle);
                cell.setCellStyle(cellStyle);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }


}

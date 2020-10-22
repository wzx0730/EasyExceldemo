package com.wzx.demo.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NetWorth {
    @ExcelProperty("代码")
    private String code;
    @ExcelProperty(value = "简称")
    private String fundName;
    @ExcelProperty("更新日期")
    private String createDate;
    @ExcelProperty("最新价")
    private String unitValue;
    @ExcelProperty("涨跌幅")
    private String dayGrowPercent;
    @ExcelProperty("日增长")
    private String dayGrowValue;

}

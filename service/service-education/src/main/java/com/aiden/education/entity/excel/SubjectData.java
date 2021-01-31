package com.aiden.education.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author Aiden
 * @version 1.0
 * @description 封装excel中的每一行数据
 * @date 2021-1-31 00:23:40
 */

@Data
public class SubjectData {
    @ExcelProperty(index = 0)
    private String oneSubjectName;
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
